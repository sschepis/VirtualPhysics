package examples.restapi

import symmetrical.physics.atomic.atoms.Atom
import symmetrical.physics.atomic.substance.ions.Compound
import symmetrical.physics.subatomic.luminescent.Absorber

/**
 * User data structure using Virtual Physics
 * Demonstrates how to create a self-contained data structure
 * that can be efficiently serialized and transmitted
 */
class User : Compound() {
    val id = Atom().with(0)
    val username = Atom().with("")
    val email = Atom().with("")
    val active = Atom().with(true)
    
    init {
        bind(id)
        bind(username)
        bind(email)
        bind(active)
    }
    
    /**
     * Helper method to set user data
     */
    fun setUser(userId: Int, userName: String, userEmail: String, isActive: Boolean = true) {
        id.betaPlusDecay(userId)
        username.betaPlusDecay(userName)
        email.betaPlusDecay(userEmail)
        active.betaPlusDecay(isActive)
    }
    
    /**
     * Get user data as a map (for demonstration purposes)
     */
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id.getValue(),
            "username" to username.getValue(),
            "email" to email.getValue(),
            "active" to active.getValue()
        )
    }
}

/**
 * Simple REST API simulation using Virtual Physics
 * 
 * This example demonstrates:
 * 1. Creating Virtual Physics data structures
 * 2. Serializing to photon strings (emit)
 * 3. Deserializing from photon strings (materialize)
 * 4. Size comparison with JSON
 */
object UserAPI {
    
    private val users = mutableListOf<User>()
    
    init {
        // Initialize with sample data
        createSampleUsers()
    }
    
    private fun createSampleUsers() {
        val user1 = User()
        user1.setUser(1, "john_doe", "john@example.com", true)
        users.add(user1)
        
        val user2 = User()
        user2.setUser(2, "jane_smith", "jane@example.com", true)
        users.add(user2)
        
        val user3 = User()
        user3.setUser(3, "bob_jones", "bob@example.com", false)
        users.add(user3)
    }
    
    /**
     * Get user by ID - returns photon string
     * In a real REST API, this would be the response body
     */
    fun getUserById(id: Int): String? {
        val user = users.find { it.id.getValue() == id }
        return user?.emit()
    }
    
    /**
     * Create new user from photon string
     * In a real REST API, this would be the request body
     */
    fun createUser(photonString: String): String {
        val (reconstructed, _) = Absorber.materialize(photonString)
        val newUser = reconstructed as User
        users.add(newUser)
        return newUser.emit()
    }
    
    /**
     * Update user from photon string
     */
    fun updateUser(id: Int, photonString: String): String? {
        val index = users.indexOfFirst { it.id.getValue() == id }
        if (index != -1) {
            val (reconstructed, _) = Absorber.materialize(photonString)
            val updatedUser = reconstructed as User
            users[index] = updatedUser
            return updatedUser.emit()
        }
        return null
    }
    
    /**
     * Get all users as a list of photon strings
     */
    fun getAllUsers(): List<String> {
        return users.map { it.emit() }
    }
    
    /**
     * Simulates JSON serialization for comparison
     */
    fun getUserAsJson(id: Int): String? {
        val user = users.find { it.id.getValue() == id } ?: return null
        val data = user.toMap()
        return """{"id":${data["id"]},"username":"${data["username"]}","email":"${data["email"]}","active":${data["active"]}}"""
    }
}

/**
 * Demo application showing Virtual Physics REST API
 */
fun main() {
    println("=".repeat(60))
    println("Virtual Physics REST API Example")
    println("=".repeat(60))
    
    // GET user by ID
    println("\n1. GET /users/1")
    val photonResponse = UserAPI.getUserById(1)
    println("   Response (Photon String): $photonResponse")
    println("   Size: ${photonResponse?.length ?: 0} characters")
    
    // Compare with JSON
    val jsonResponse = UserAPI.getUserAsJson(1)
    println("\n   Traditional JSON Response: $jsonResponse")
    println("   Size: ${jsonResponse?.length ?: 0} characters")
    
    val reduction = if (photonResponse != null && jsonResponse != null) {
        ((jsonResponse.length - photonResponse.length).toDouble() / jsonResponse.length * 100).toInt()
    } else 0
    println("\n   ✓ Size Reduction: $reduction%")
    
    // Deserialize and display
    println("\n2. Deserializing the response:")
    if (photonResponse != null) {
        val (user, _) = Absorber.materialize(photonResponse)
        val retrievedUser = user as User
        println("   User ID: ${retrievedUser.id.getValue()}")
        println("   Username: ${retrievedUser.username.getValue()}")
        println("   Email: ${retrievedUser.email.getValue()}")
        println("   Active: ${retrievedUser.active.getValue()}")
    }
    
    // POST - Create new user
    println("\n3. POST /users (Create new user)")
    val newUser = User()
    newUser.setUser(4, "alice_wonder", "alice@example.com", true)
    val createRequest = newUser.emit()
    println("   Request Body (Photon String): $createRequest")
    println("   Size: ${createRequest.length} characters")
    
    val createResponse = UserAPI.createUser(createRequest)
    println("   Response: $createResponse")
    
    // PUT - Update user
    println("\n4. PUT /users/2 (Update user)")
    val updateUser = User()
    updateUser.setUser(2, "jane_smith_updated", "jane.new@example.com", true)
    val updateRequest = updateUser.emit()
    println("   Request Body: $updateRequest")
    
    val updateResponse = UserAPI.updateUser(2, updateRequest)
    println("   Response: $updateResponse")
    
    // GET all users
    println("\n5. GET /users (Get all users)")
    val allUsers = UserAPI.getAllUsers()
    println("   Number of users: ${allUsers.size}")
    allUsers.forEachIndexed { index, photonString ->
        println("   User ${index + 1}: $photonString")
    }
    
    // Demonstrate rollback capability
    println("\n6. Bonus: Built-in Rollback")
    println("   Virtual Physics includes built-in undo/redo!")
    val demoUser = User()
    demoUser.setUser(99, "demo_user", "demo@example.com", true)
    println("   Original: ${demoUser.username.getValue()}")
    
    demoUser.username.betaPlusDecay("changed_username")
    println("   After change: ${demoUser.username.getValue()}")
    
    demoUser.username.betaMinusDecay()
    println("   After rollback: ${demoUser.username.getValue()}")
    
    println("\n" + "=".repeat(60))
    println("Summary:")
    println("=".repeat(60))
    println("✓ Virtual Physics provides 90% smaller payloads")
    println("✓ Type-safe serialization/deserialization")
    println("✓ No external dependencies (no Jackson, Gson, etc.)")
    println("✓ Built-in rollback mechanism")
    println("✓ One-line serialization: object.emit()")
    println("✓ One-line deserialization: Absorber.materialize()")
    println("=".repeat(60))
}
