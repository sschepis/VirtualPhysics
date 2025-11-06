# Rollback Example - Virtual Physics

This example demonstrates the built-in rollback mechanism in Virtual Physics using beta decay.

## Overview

Virtual Physics includes a revolutionary built-in undo/redo mechanism inspired by nuclear physics beta decay:

- **Beta Plus Decay** (`betaPlusDecay(newValue)`): Changes value and stores the previous value as a "neutron"
- **Beta Minus Decay** (`betaMinusDecay()`): Restores the previous value from the neutron

This provides native rollback capability without external libraries or complex state management.

## What's Demonstrated

### 1. Simple Rollback
Basic undo functionality on a single atom:
```kotlin
val atom = Atom().with("Original")
atom.betaPlusDecay("Changed")  // Store "Original", set "Changed"
atom.betaMinusDecay()          // Restore "Original"
```

### 2. Multiple Rollbacks
Full version history with multiple undo levels:
```kotlin
atom.betaPlusDecay("Version 2")
atom.betaPlusDecay("Version 3")
atom.betaPlusDecay("Version 4")

atom.betaMinusDecay()  // Back to Version 3
atom.betaMinusDecay()  // Back to Version 2
atom.betaMinusDecay()  // Back to Version 1
```

### 3. Document Editing
Practical example with a document editor:
- Edit document multiple times
- Undo changes to previous versions
- Full document state restoration

### 4. Form Cancellation
Enable "Cancel" buttons in forms:
- User makes changes
- User clicks Cancel
- All changes reverted automatically

### 5. Transactional Operations
Transaction-like behavior:
- Start operation
- Make changes
- Validation fails
- Rollback everything

### 6. State Machine Recovery
Error recovery in state machines:
- Transition through states
- Error occurs
- Rollback to previous stable state
- Continue from there

## Running the Example

### From the main Virtual Physics directory:

```bash
# Copy to test directory
cp examples/rollback/src/main/kotlin/RollbackExamples.kt \
   src/test/kotlin/applications/RollbackExamples.kt

# Build and run
./gradlew build
```

Or create a test wrapper to run it.

## Expected Output

```
╔══════════════════════════════════════════════════════════╗
║  Virtual Physics Rollback Examples                        ║
╚══════════════════════════════════════════════════════════╝

============================================================
Example 1: Simple Rollback
============================================================
Initial value: Original Value
After change: Changed Value
After rollback: Original Value
✓ Value restored!

============================================================
Example 2: Multiple Rollbacks (Version History)
============================================================
Version 1: Version 1
Version 2: Version 2
Version 3: Version 3
Version 4: Version 4

Rolling back through versions:
After rollback 1: Version 3
After rollback 2: Version 2
After rollback 3: Version 1
✓ Full version history maintained!

============================================================
Example 3: Document Editing with Undo
============================================================
Creating new document:
  Title: First Draft
  Content: This is the initial content.
  Author: John Doe
  Version: 1

Editing document (version 2):
  Title: Second Draft
  Content: This is improved content with more details.
  Author: John Doe
  Version: 2

Editing document again (version 3):
  Title: Final Draft
  Content: This is the final polished content.
  Author: John Doe
  Version: 3

Oops! Let's undo the last change:
  Title: Second Draft
  Content: This is improved content with more details.
  Author: John Doe
  Version: 2

Actually, let's undo one more time:
  Title: First Draft
  Content: This is the initial content.
  Author: John Doe
  Version: 1
✓ Document reverted to original state!

[... more examples ...]

============================================================
Key Concepts Summary
============================================================
✓ betaPlusDecay(newValue): Change value, store previous
✓ betaMinusDecay(): Restore previous value
✓ Works on Atoms, Molecules, and Compounds
✓ Multiple levels of undo supported
✓ Perfect for: Forms, Transactions, State Machines
✓ No external libraries needed
✓ Physics-inspired metaphor (nuclear beta decay)
============================================================
```

## The Physics Metaphor

### Beta Decay in Nuclear Physics

In real physics, beta decay involves converting neutrons to protons (or vice versa):
- **Beta Plus (β+)**: Proton → Neutron + Positron + Neutrino
- **Beta Minus (β-)**: Neutron → Proton + Electron + Antineutrino

### Beta Decay in Virtual Physics

Virtual Physics adapts this concept for data versioning:
- **betaPlusDecay(newValue)**: Store current value as "neutron", set new value
- **betaMinusDecay()**: Restore value from "neutron"

This makes atoms "heavier" (store more data) but enables powerful rollback capabilities.

## Real-World Applications

### 1. Form Editing
```kotlin
class UserForm : Compound() {
    val name = Atom().with("")
    val email = Atom().with("")
    
    fun save() {
        // Changes are already applied
    }
    
    fun cancel() {
        // Rollback all fields
        name.betaMinusDecay()
        email.betaMinusDecay()
    }
}
```

### 2. Undo/Redo Stack
```kotlin
class Editor {
    val content = Atom().with("")
    
    fun type(text: String) {
        content.betaPlusDecay(content.getValue() + text)
    }
    
    fun undo() {
        content.betaMinusDecay()
    }
}
```

### 3. Transaction Management
```kotlin
class Database {
    val record = Atom().with(initialData)
    
    fun beginTransaction() {
        // Current state automatically saved
    }
    
    fun commit() {
        // Keep changes
    }
    
    fun rollback() {
        record.betaMinusDecay()
    }
}
```

### 4. A/B Testing
```kotlin
val configuration = Atom().with("VersionA")

fun testVersionB() {
    configuration.betaPlusDecay("VersionB")
    // Test...
    if (!successfulTest) {
        configuration.betaMinusDecay()  // Back to A
    }
}
```

### 5. Optimistic Updates
```kotlin
val data = Atom().with(currentData)

fun optimisticUpdate(newData: Any) {
    data.betaPlusDecay(newData)  // Show immediately
    
    apiCall {
        if (failed) {
            data.betaMinusDecay()  // Rollback on failure
        }
    }
}
```

## Comparison with Traditional Approaches

### Traditional Undo/Redo
```kotlin
// Requires manual state tracking
class Editor {
    private val history = mutableListOf<String>()
    private var currentIndex = -1
    
    fun setText(text: String) {
        history.add(text)
        currentIndex++
    }
    
    fun undo() {
        if (currentIndex > 0) {
            currentIndex--
            return history[currentIndex]
        }
    }
    
    fun redo() {
        if (currentIndex < history.size - 1) {
            currentIndex++
            return history[currentIndex]
        }
    }
}
```

### Virtual Physics Approach
```kotlin
// Built-in, no manual tracking needed
val content = Atom().with("")

content.betaPlusDecay("new text")  // Change with auto-save
content.betaMinusDecay()           // Undo
content.betaPlusDecay("...")       // Redo (by setting new value)
```

## Advantages

1. **Zero Configuration**: Works out of the box
2. **Type Safe**: Full compile-time type checking
3. **No External Libraries**: Built into Virtual Physics
4. **Consistent**: Same API across all data structures
5. **Intuitive**: Physics metaphor makes it memorable
6. **Lightweight**: No complex state management overhead
7. **Reliable**: Cannot forget to track state

## Limitations

- Each change stores previous value (memory overhead)
- Deep undo/redo may require multiple decay calls
- Not suitable for extremely large data structures with frequent changes

For most applications, these trade-offs are well worth the convenience!

## Next Steps

- Combine with data binding for reactive undo/redo
- Explore compound rollback for complex objects
- Build a text editor with full undo/redo
- Implement form validation with rollback

## Learn More

- Main documentation: [README.MD](../../README.MD)
- Getting started: [GETTING_STARTED.md](../../GETTING_STARTED.md)
- Data binding: [examples/data-binding/](../data-binding/)

## Questions?

Open an issue on GitHub or check the existing documentation.
