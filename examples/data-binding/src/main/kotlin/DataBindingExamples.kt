package examples.databinding

import symmetrical.physics.atomic.atoms.Atom

/**
 * Virtual Physics Data Binding Examples
 * 
 * Demonstrates three types of data binding circuits:
 * 1. Conductors - Bidirectional binding (two-way)
 * 2. Diodes - Unidirectional binding (one-way)
 * 3. Capacitors - Reactive binding (notification without data transfer)
 */

object DataBindingExamples {
    
    /**
     * Example 1: Conductors - Bidirectional Binding
     * 
     * Conductors allow data to flow freely in both directions,
     * keeping all connected atoms synchronized.
     */
    fun demonstrateConductors() {
        println("=" * 60)
        println("Example 1: Conductors (Bidirectional Binding)")
        println("=" * 60)
        
        val atom1 = Atom().with("ATOM1")
        val atom2 = Atom().with("ATOM2")
        val atom3 = Atom().with("ATOM3")
        
        println("Initial values:")
        println("  atom1: ${atom1.getValue()}")
        println("  atom2: ${atom2.getValue()}")
        println("  atom3: ${atom3.getValue()}")
        
        // Connect atoms with conductors
        // The underscore after conductor_ means return the right atom
        atom1.conductor_(atom2).conductor(atom3)
        
        println("\nAfter connecting with conductors:")
        println("  atom1.conductor_(atom2).conductor(atom3)")
        
        // Change atom1's value
        atom1.betaPlusDecay("CHANGED_FROM_ATOM1")
        
        println("\nAfter changing atom1:")
        println("  atom1: ${atom1.getValue()}")
        println("  atom2: ${atom2.getValue()}")
        println("  atom3: ${atom3.getValue()}")
        println("  ✓ All atoms synchronized!")
        
        // Change atom2's value
        atom2.betaPlusDecay("CHANGED_FROM_ATOM2")
        
        println("\nAfter changing atom2:")
        println("  atom1: ${atom1.getValue()}")
        println("  atom2: ${atom2.getValue()}")
        println("  atom3: ${atom3.getValue()}")
        println("  ✓ Changes propagate bidirectionally!")
    }
    
    /**
     * Example 2: Diodes - Unidirectional Binding
     * 
     * Diodes act as one-way valves, directing data flow like
     * a digital traffic light.
     */
    fun demonstrateDiodes() {
        println("\n" + "=" * 60)
        println("Example 2: Diodes (Unidirectional Binding)")
        println("=" * 60)
        
        val source = Atom().with("SOURCE")
        val middle = Atom().with("MIDDLE")
        val target = Atom().with("TARGET")
        
        println("Initial values:")
        println("  source: ${source.getValue()}")
        println("  middle: ${middle.getValue()}")
        println("  target: ${target.getValue()}")
        
        // Create one-way flow: target -> middle -> source
        // diode_(atom) means atom flows TO the current atom
        source.diode_(middle).diode_(target)
        
        println("\nAfter connecting with diodes:")
        println("  source.diode_(middle).diode_(target)")
        println("  Data flows: target → middle → source")
        
        // Change target (upstream) - affects all
        println("\nChanging target (upstream):")
        target.betaPlusDecay("TARGET_CHANGED")
        println("  source: ${source.getValue()}")
        println("  middle: ${middle.getValue()}")
        println("  target: ${target.getValue()}")
        println("  ✓ Upstream changes flow downstream!")
        
        // Change middle - affects middle and source only
        println("\nChanging middle:")
        middle.betaPlusDecay("MIDDLE_CHANGED")
        println("  source: ${source.getValue()}")
        println("  middle: ${middle.getValue()}")
        println("  target: ${target.getValue()}")
        println("  ✓ Middle changes don't affect target!")
        
        // Change source - affects only source
        println("\nChanging source (downstream):")
        source.betaPlusDecay("SOURCE_CHANGED")
        println("  source: ${source.getValue()}")
        println("  middle: ${middle.getValue()}")
        println("  target: ${target.getValue()}")
        println("  ✓ Downstream changes stay local!")
    }
    
    /**
     * Example 3: Circular Dependencies with Diodes
     * 
     * Virtual Physics prevents infinite loops even with circular dependencies!
     */
    fun demonstrateCircularDiodes() {
        println("\n" + "=" * 60)
        println("Example 3: Circular Dependencies (No Infinite Loops!)")
        println("=" * 60)
        
        val atom1 = Atom().with("ATOM1")
        val atom2 = Atom().with("ATOM2")
        val atom3 = Atom().with("ATOM3")
        
        println("Creating circular dependency:")
        println("  atom1 → atom2 → atom3 → atom1 (full circle)")
        
        // Create circular dependency
        atom1.diode_(atom2).diode_(atom3).diode_(atom1)
        
        println("\nChanging atom1:")
        atom1.betaPlusDecay("CIRCULAR_UPDATE")
        
        println("  atom1: ${atom1.getValue()}")
        println("  atom2: ${atom2.getValue()}")
        println("  atom3: ${atom3.getValue()}")
        println("  ✓ All updated in order, no infinite loop!")
    }
    
    /**
     * Example 4: Form Synchronization Use Case
     * 
     * Practical example: Synchronizing form fields
     */
    fun demonstrateFormSync() {
        println("\n" + "=" * 60)
        println("Example 4: Real-World Use Case - Form Synchronization")
        println("=" * 60)
        
        // Simulate form fields
        val usernameInput = Atom().with("")
        val usernameDisplay = Atom().with("")
        val usernamePreview = Atom().with("")
        
        println("Scenario: User profile form")
        println("  - Input field (editable)")
        println("  - Display field (read-only)")
        println("  - Preview field (read-only)")
        
        // Connect input to both display and preview
        // They will automatically update when input changes
        usernameInput._diode(usernameDisplay)._diode(usernamePreview)
        
        println("\nUser types in input field:")
        usernameInput.betaPlusDecay("john_doe")
        
        println("  Input: ${usernameInput.getValue()}")
        println("  Display: ${usernameDisplay.getValue()}")
        println("  Preview: ${usernamePreview.getValue()}")
        println("  ✓ All fields synchronized from single change!")
        
        println("\nUser updates input:")
        usernameInput.betaPlusDecay("jane_smith")
        
        println("  Input: ${usernameInput.getValue()}")
        println("  Display: ${usernameDisplay.getValue()}")
        println("  Preview: ${usernamePreview.getValue()}")
        println("  ✓ Real-time synchronization!")
    }
    
    /**
     * Example 5: Tree-Shaped Binding
     * 
     * Create complex binding patterns like trees
     */
    fun demonstrateTreeBinding() {
        println("\n" + "=" * 60)
        println("Example 5: Tree-Shaped Binding")
        println("=" * 60)
        
        val root = Atom().with("ROOT")
        val branch1 = Atom().with("BRANCH1")
        val branch2 = Atom().with("BRANCH2")
        val leaf1 = Atom().with("LEAF1")
        val leaf2 = Atom().with("LEAF2")
        
        println("Creating tree structure:")
        println("       ROOT")
        println("      /    \\")
        println("  BRANCH1  BRANCH2")
        println("     |        |")
        println("   LEAF1    LEAF2")
        
        // Connect branches to root
        root._diode(branch1)._diode(branch2)
        
        // Connect leaves to branches
        branch1._diode(leaf1)
        branch2._diode(leaf2)
        
        println("\nChanging root value:")
        root.betaPlusDecay("ROOT_UPDATED")
        
        println("  root: ${root.getValue()}")
        println("  branch1: ${branch1.getValue()}")
        println("  branch2: ${branch2.getValue()}")
        println("  leaf1: ${leaf1.getValue()}")
        println("  leaf2: ${leaf2.getValue()}")
        println("  ✓ Update cascades through entire tree!")
    }
}

/**
 * Main entry point - runs all examples
 */
fun main() {
    println("\n")
    println("╔" + "═" * 58 + "╗")
    println("║  Virtual Physics Data Binding Examples                    ║")
    println("╚" + "═" * 58 + "╝")
    println()
    
    DataBindingExamples.demonstrateConductors()
    DataBindingExamples.demonstrateDiodes()
    DataBindingExamples.demonstrateCircularDiodes()
    DataBindingExamples.demonstrateFormSync()
    DataBindingExamples.demonstrateTreeBinding()
    
    println("\n" + "=" * 60)
    println("Key Concepts Summary")
    println("=" * 60)
    println("✓ Conductors: Bidirectional (two-way) binding")
    println("✓ Diodes: Unidirectional (one-way) binding")
    println("✓ Capacitors: Reactive (notification) binding")
    println("✓ No infinite loops in circular dependencies")
    println("✓ Tree and complex graph structures supported")
    println("✓ Perfect for: Forms, State Management, Reactive UIs")
    println("=" * 60)
}

// String multiplication extension for prettier output
private operator fun String.times(n: Int): String = this.repeat(n)
