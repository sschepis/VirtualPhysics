# Getting Started with Virtual Physics

Welcome to Virtual Physics! This guide will help you get started with building physics-inspired software systems.

## Table of Contents

- [What is Virtual Physics?](#what-is-virtual-physics)
- [Installation](#installation)
- [Quick Start](#quick-start)
- [Core Concepts](#core-concepts)
- [Your First Virtual Physics Application](#your-first-virtual-physics-application)
- [Next Steps](#next-steps)

## What is Virtual Physics?

Virtual Physics is a revolutionary framework that applies fundamental physics concepts to software engineering. It provides:

- **Efficient Serialization**: Emit and absorb data with 90% smaller payload than JSON/XML
- **Data Binding**: Three-tier binding system (Conductors, Diodes, Capacitors)
- **Built-in Rollback**: Beta decay mechanism for native undo/redo functionality
- **Self-Describing Data**: Elements based on the periodic table
- **Zero Dependencies**: Built entirely on Kotlin and JVM

## Installation

### Prerequisites

- JDK 17 or higher
- Gradle 8.5 or higher (or use the included wrapper)
- IntelliJ IDEA (recommended)

### Clone the Repository

```bash
git clone https://github.com/sschepis/VirtualPhysics.git
cd VirtualPhysics
```

### Build the Project

Using Gradle wrapper:
```bash
./gradlew build
```

Or using your installed Gradle:
```bash
gradle build
```

### Using in Your Project

#### Option 1: Include as Source

Copy the `src/main/kotlin` directory into your project.

#### Option 2: Build and Use JAR

```bash
./gradlew jar
```

The JAR will be created in `build/libs/`. Add it to your project's classpath.

#### Option 3: Local Maven (Coming Soon)

```bash
./gradlew publishToMavenLocal
```

Then add to your `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.monastery:virtual-physics:1.0.0-SNAPSHOT")
}
```

## Quick Start

### Example 1: Effortless Data Transmission

Virtual Physics makes data transmission incredibly simple with built-in serialization:

```kotlin
import symmetrical.physics.atomic.atoms.Atom
import symmetrical.physics.subatomic.luminescent.Absorber

// Create an atom with data
val atom = Atom().with("Hello, Virtual Physics!")

// Serialize to photon string (emit)
val emission = atom.emit()

// Transmit emission to another system...
// (emission is a compact string representation)

// Deserialize from photon string (absorb)
val (reconstructedAtom, _) = Absorber.materialize(emission)
val value = (reconstructedAtom as Atom).getValue()

println(value) // Output: Hello, Virtual Physics!
```

### Example 2: Data Binding with Conductors

Create two-way data binding between atoms:

```kotlin
val atom1 = Atom().with("ATOM1")
val atom2 = Atom().with("ATOM2")
val atom3 = Atom().with("ATOM3")

// Connect atoms with conductors (bidirectional binding)
atom1.conductor_(atom2).conductor(atom3)

// Change atom1's value
atom1.betaPlusDecay("NEW_VALUE")

// All atoms now have the same value
println(atom1.getValue()) // NEW_VALUE
println(atom2.getValue()) // NEW_VALUE
println(atom3.getValue()) // NEW_VALUE
```

### Example 3: One-Way Binding with Diodes

Control the flow of data with diodes:

```kotlin
val source = Atom().with("SOURCE")
val middle = Atom().with("MIDDLE")
val target = Atom().with("TARGET")

// Create one-way data flow: source -> middle -> target
source.diode_(middle).diode_(target)

// Change target value - affects all atoms
target.betaPlusDecay("CHANGED")

// Change middle - only affects middle and source
middle.betaPlusDecay("MIDDLE_CHANGED")

// Change source - only affects source
source.betaPlusDecay("SOURCE_CHANGED")
```

### Example 4: Built-in Rollback

Use beta decay for rollback functionality:

```kotlin
val atom = Atom().with("Original Value")

// Change the value (stores previous value as neutron)
atom.betaPlusDecay("New Value")
println(atom.getValue()) // New Value

// Rollback to previous value
atom.betaMinusDecay()
println(atom.getValue()) // Original Value
```

## Core Concepts

### Atoms

Atoms are the fundamental building blocks representing individual data fields:

```kotlin
import symmetrical.physics.atomic.atoms.Atom

val atom = Atom().with("Data Value")
```

### Molecules

Molecules combine atoms to create small structures:

```kotlin
import symmetrical.physics.atomic.substance.molecules.Molecule

class PersonMolecule : Molecule() {
    val name = Atom().with("")
    val age = Atom().with(0)
    
    init {
        bind(name)
        bind(age)
    }
}
```

### Compounds

Compounds are complex structures similar to objects or collections:

```kotlin
import symmetrical.physics.atomic.substance.ions.Compound

class AddressCompound : Compound() {
    val street = Atom().with("")
    val city = Atom().with("")
    val zipCode = Atom().with("")
    
    init {
        bind(street)
        bind(city)
        bind(zipCode)
    }
}
```

### Emission and Absorption

Convert any structure to a compact photon string:

```kotlin
val compound = AddressCompound()
compound.street.betaPlusDecay("123 Main St")
compound.city.betaPlusDecay("Springfield")
compound.zipCode.betaPlusDecay("12345")

// Emit to photon string
val photonString = compound.emit()

// Later, absorb and reconstruct
val (reconstructed, _) = Absorber.materialize(photonString)
val address = reconstructed as AddressCompound
```

### Data Binding Circuits

Three types of connections:

1. **Conductors**: Bidirectional data flow
   ```kotlin
   atom1.conductor_(atom2)
   ```

2. **Diodes**: One-way data flow
   ```kotlin
   atom1.diode_(atom2) // atom2 -> atom1
   ```

3. **Capacitors**: Reactive binding without data exchange
   ```kotlin
   atom1.capacitor_(atom2)
   ```

### Beta Decay (Rollback)

Store previous values and rollback:

```kotlin
atom.betaPlusDecay("new value")  // Save current, set new
atom.betaMinusDecay()             // Restore previous value
```

## Your First Virtual Physics Application

Let's build a simple contact management system:

```kotlin
import symmetrical.physics.atomic.atoms.Atom
import symmetrical.physics.atomic.substance.ions.Compound
import symmetrical.physics.subatomic.luminescent.Absorber

// Define a Contact structure
class Contact : Compound() {
    val name = Atom().with("")
    val email = Atom().with("")
    val phone = Atom().with("")
    
    init {
        bind(name)
        bind(email)
        bind(phone)
    }
    
    fun setContact(nameVal: String, emailVal: String, phoneVal: String) {
        name.betaPlusDecay(nameVal)
        email.betaPlusDecay(emailVal)
        phone.betaPlusDecay(phoneVal)
    }
    
    fun display() {
        println("Name: ${name.getValue()}")
        println("Email: ${email.getValue()}")
        println("Phone: ${phone.getValue()}")
    }
}

fun main() {
    // Create a contact
    val contact = Contact()
    contact.setContact("John Doe", "john@example.com", "555-1234")
    
    println("Original contact:")
    contact.display()
    
    // Serialize the contact
    val emission = contact.emit()
    println("\nSerialized: $emission")
    println("Size: ${emission.length} characters")
    
    // Transmit emission...
    // (In real application, send over network)
    
    // Deserialize on another system
    val (reconstructed, _) = Absorber.materialize(emission)
    val receivedContact = reconstructed as Contact
    
    println("\nReconstructed contact:")
    receivedContact.display()
    
    // Demonstrate rollback
    println("\n--- Testing Rollback ---")
    contact.setContact("Jane Smith", "jane@example.com", "555-5678")
    println("\nAfter update:")
    contact.display()
    
    // Rollback all fields
    contact.name.betaMinusDecay()
    contact.email.betaMinusDecay()
    contact.phone.betaMinusDecay()
    
    println("\nAfter rollback:")
    contact.display()
}
```

## Next Steps

### Learn More

- **Documentation**: Check the `documentation/` directory for UML diagrams
- **Examples**: Explore `src/test/kotlin/applications/tests/` for more examples
- **Physics Concepts**: Review the Wikipedia links in source files to understand the metaphors

### Explore Advanced Features

1. **Polymers and Filters**: Learn about chemistry-based data filtering
2. **Gravity**: Understand variable sharing without data pollution
3. **Elements**: Create self-describing building blocks
4. **Quantum Features**: Explore advanced physics concepts (coming soon)

### Try These Examples

- **REST API**: Build a REST API using Virtual Physics for data transfer
- **Real-time Chat**: Create a WebSocket chat with data binding
- **State Management**: Implement undo/redo in your application
- **Full-Stack App**: Build a Kotlin/JS + Ktor application

### Get Involved

- **Contribute**: Check [CONTRIBUTING.md](CONTRIBUTING.md) for contribution guidelines
- **Report Issues**: Found a bug? Create an issue on GitHub
- **Join Discussion**: Participate in community forums
- **Share Your Work**: Show us what you build with Virtual Physics!

## Resources

- **Main Documentation**: [README.MD](README.MD)
- **Video Tutorial**: [Intro to Virtual Physics](https://www.youtube.com/watch?v=0u_rUdT7cTg&t=99s)
- **Source Code**: Explore the well-documented source code
- **UML Diagrams**: Visual representations in `documentation/uml/`

## Need Help?

If you run into issues or have questions:

1. Check the existing documentation and examples
2. Search existing GitHub issues
3. Create a new issue with details about your problem
4. Join the community forum for discussions

Welcome to the future of physics-inspired software engineering! 🚀
