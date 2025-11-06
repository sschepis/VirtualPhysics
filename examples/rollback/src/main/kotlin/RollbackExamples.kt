package examples.rollback

import symmetrical.physics.atomic.atoms.Atom
import symmetrical.physics.atomic.substance.ions.Compound

/**
 * Document structure for demonstrating rollback
 */
class Document : Compound() {
    val title = Atom().with("")
    val content = Atom().with("")
    val author = Atom().with("")
    val version = Atom().with(1)
    
    init {
        bind(title)
        bind(content)
        bind(author)
        bind(version)
    }
    
    fun setDocument(titleVal: String, contentVal: String, authorVal: String, versionVal: Int = 1) {
        title.betaPlusDecay(titleVal)
        content.betaPlusDecay(contentVal)
        author.betaPlusDecay(authorVal)
        version.betaPlusDecay(versionVal)
    }
    
    fun display() {
        println("  Title: ${title.getValue()}")
        println("  Content: ${content.getValue()}")
        println("  Author: ${author.getValue()}")
        println("  Version: ${version.getValue()}")
    }
    
    fun rollback() {
        title.betaMinusDecay()
        content.betaMinusDecay()
        author.betaMinusDecay()
        version.betaMinusDecay()
    }
}

/**
 * Virtual Physics Rollback Examples
 * 
 * Demonstrates beta decay mechanism for built-in undo/redo functionality
 */
object RollbackExamples {
    
    /**
     * Example 1: Simple Rollback
     * 
     * Shows basic rollback on a single atom
     */
    fun demonstrateSimpleRollback() {
        println("=" * 60)
        println("Example 1: Simple Rollback")
        println("=" * 60)
        
        val atom = Atom().with("Original Value")
        println("Initial value: ${atom.getValue()}")
        
        // Change value using beta plus decay (stores previous value)
        atom.betaPlusDecay("Changed Value")
        println("After change: ${atom.getValue()}")
        
        // Rollback using beta minus decay
        atom.betaMinusDecay()
        println("After rollback: ${atom.getValue()}")
        println("✓ Value restored!")
    }
    
    /**
     * Example 2: Multiple Rollbacks
     * 
     * Shows that multiple changes can be tracked and rolled back
     */
    fun demonstrateMultipleRollbacks() {
        println("\n" + "=" * 60)
        println("Example 2: Multiple Rollbacks (Version History)")
        println("=" * 60)
        
        val atom = Atom().with("Version 1")
        println("Version 1: ${atom.getValue()}")
        
        atom.betaPlusDecay("Version 2")
        println("Version 2: ${atom.getValue()}")
        
        atom.betaPlusDecay("Version 3")
        println("Version 3: ${atom.getValue()}")
        
        atom.betaPlusDecay("Version 4")
        println("Version 4: ${atom.getValue()}")
        
        println("\nRolling back through versions:")
        
        atom.betaMinusDecay()
        println("After rollback 1: ${atom.getValue()}")
        
        atom.betaMinusDecay()
        println("After rollback 2: ${atom.getValue()}")
        
        atom.betaMinusDecay()
        println("After rollback 3: ${atom.getValue()}")
        
        println("✓ Full version history maintained!")
    }
    
    /**
     * Example 3: Document Editing with Undo
     * 
     * Practical example: editing a document with undo functionality
     */
    fun demonstrateDocumentEditing() {
        println("\n" + "=" * 60)
        println("Example 3: Document Editing with Undo")
        println("=" * 60)
        
        val doc = Document()
        
        println("Creating new document:")
        doc.setDocument("First Draft", "This is the initial content.", "John Doe", 1)
        doc.display()
        
        println("\nEditing document (version 2):")
        doc.setDocument("Second Draft", "This is improved content with more details.", "John Doe", 2)
        doc.display()
        
        println("\nEditing document again (version 3):")
        doc.setDocument("Final Draft", "This is the final polished content.", "John Doe", 3)
        doc.display()
        
        println("\nOops! Let's undo the last change:")
        doc.rollback()
        doc.display()
        
        println("\nActually, let's undo one more time:")
        doc.rollback()
        doc.display()
        
        println("✓ Document reverted to original state!")
    }
    
    /**
     * Example 4: Form Editing with Cancel
     * 
     * Shows how rollback enables "Cancel" functionality in forms
     */
    fun demonstrateFormCancellation() {
        println("\n" + "=" * 60)
        println("Example 4: Form Editing with Cancel Button")
        println("=" * 60)
        
        val username = Atom().with("john_doe")
        val email = Atom().with("john@example.com")
        
        println("Original profile:")
        println("  Username: ${username.getValue()}")
        println("  Email: ${email.getValue()}")
        
        println("\nUser starts editing:")
        username.betaPlusDecay("jane_smith")
        email.betaPlusDecay("jane@example.com")
        
        println("  Username: ${username.getValue()}")
        println("  Email: ${email.getValue()}")
        
        println("\nUser clicks 'Cancel' button:")
        username.betaMinusDecay()
        email.betaMinusDecay()
        
        println("  Username: ${username.getValue()}")
        println("  Email: ${email.getValue()}")
        println("✓ All changes reverted!")
    }
    
    /**
     * Example 5: Transactional Operations
     * 
     * Shows how rollback enables transaction-like behavior
     */
    fun demonstrateTransactionalOperations() {
        println("\n" + "=" * 60)
        println("Example 5: Transactional Operations")
        println("=" * 60)
        
        val balance = Atom().with(1000)
        val transactionLog = Atom().with("No transactions")
        
        println("Initial state:")
        println("  Balance: $${balance.getValue()}")
        println("  Log: ${transactionLog.getValue()}")
        
        println("\nStarting transaction: Withdraw $200")
        balance.betaPlusDecay(800)
        transactionLog.betaPlusDecay("Withdrew $200")
        
        println("  Balance: $${balance.getValue()}")
        println("  Log: ${transactionLog.getValue()}")
        
        println("\nValidation failed! Rolling back transaction:")
        balance.betaMinusDecay()
        transactionLog.betaMinusDecay()
        
        println("  Balance: $${balance.getValue()}")
        println("  Log: ${transactionLog.getValue()}")
        println("✓ Transaction rolled back safely!")
    }
    
    /**
     * Example 6: State Machine with Rollback
     * 
     * Shows rollback in state management
     */
    fun demonstrateStateMachine() {
        println("\n" + "=" * 60)
        println("Example 6: State Machine with Rollback")
        println("=" * 60)
        
        val state = Atom().with("IDLE")
        
        println("State transitions:")
        println("  State: ${state.getValue()}")
        
        state.betaPlusDecay("LOADING")
        println("  State: ${state.getValue()}")
        
        state.betaPlusDecay("PROCESSING")
        println("  State: ${state.getValue()}")
        
        state.betaPlusDecay("ERROR")
        println("  State: ${state.getValue()}")
        
        println("\nError occurred! Reverting to previous state:")
        state.betaMinusDecay()
        println("  State: ${state.getValue()}")
        
        println("\nLet's try again from here...")
        state.betaPlusDecay("SUCCESS")
        println("  State: ${state.getValue()}")
        println("✓ State machine recovered from error!")
    }
}

/**
 * Main entry point - runs all examples
 */
fun main() {
    println("\n")
    println("╔" + "═" * 58 + "╗")
    println("║  Virtual Physics Rollback Examples                        ║")
    println("╚" + "═" * 58 + "╝")
    println()
    
    RollbackExamples.demonstrateSimpleRollback()
    RollbackExamples.demonstrateMultipleRollbacks()
    RollbackExamples.demonstrateDocumentEditing()
    RollbackExamples.demonstrateFormCancellation()
    RollbackExamples.demonstrateTransactionalOperations()
    RollbackExamples.demonstrateStateMachine()
    
    println("\n" + "=" * 60)
    println("Key Concepts Summary")
    println("=" * 60)
    println("✓ betaPlusDecay(newValue): Change value, store previous")
    println("✓ betaMinusDecay(): Restore previous value")
    println("✓ Works on Atoms, Molecules, and Compounds")
    println("✓ Multiple levels of undo supported")
    println("✓ Perfect for: Forms, Transactions, State Machines")
    println("✓ No external libraries needed")
    println("✓ Physics-inspired metaphor (nuclear beta decay)")
    println("=" * 60)
}

// String multiplication extension for prettier output
private operator fun String.times(n: Int): String = this.repeat(n)
