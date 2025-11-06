# Data Binding Example - Virtual Physics

This example demonstrates the three-tier data binding system in Virtual Physics.

## Overview

Virtual Physics provides three types of data binding circuits inspired by electronic components:

1. **Conductors** - Bidirectional binding (two-way data flow)
2. **Diodes** - Unidirectional binding (one-way data flow)
3. **Capacitors** - Reactive binding (notifications without data transfer)

## What's Demonstrated

### 1. Conductors (Bidirectional)
Conductors allow data to flow freely in both directions:
```kotlin
atom1.conductor_(atom2).conductor(atom3)
// Any change to any atom updates all connected atoms
```

**Use Cases:**
- Form fields that need to stay in sync
- State synchronization
- Real-time collaborative editing

### 2. Diodes (Unidirectional)
Diodes act as one-way valves:
```kotlin
source.diode_(middle).diode_(target)
// Changes flow: target → middle → source
```

**Use Cases:**
- Computed properties
- Derived state
- Cascade updates

### 3. Circular Dependencies
Virtual Physics safely handles circular dependencies:
```kotlin
atom1.diode_(atom2).diode_(atom3).diode_(atom1)
// Full circle, but no infinite loop!
```

### 4. Tree Structures
Create complex binding patterns:
```kotlin
root._diode(branch1)._diode(branch2)
// Updates cascade through the tree
```

## Running the Example

### From the main Virtual Physics directory:

```bash
# Copy to test directory (temporary)
cp examples/data-binding/src/main/kotlin/DataBindingExamples.kt \
   src/test/kotlin/applications/DataBindingExamples.kt

# Compile and run
./gradlew build
kotlin -cp build/classes/kotlin/test:build/classes/kotlin/main \
    applications.DataBindingExamples
```

### Or integrate into your test suite:

Add to `src/test/kotlin/applications/RunMe.kt` or create a test wrapper.

## Expected Output

```
╔══════════════════════════════════════════════════════════╗
║  Virtual Physics Data Binding Examples                    ║
╚══════════════════════════════════════════════════════════╝

============================================================
Example 1: Conductors (Bidirectional Binding)
============================================================
Initial values:
  atom1: ATOM1
  atom2: ATOM2
  atom3: ATOM3

After connecting with conductors:
  atom1.conductor_(atom2).conductor(atom3)

After changing atom1:
  atom1: CHANGED_FROM_ATOM1
  atom2: CHANGED_FROM_ATOM1
  atom3: CHANGED_FROM_ATOM1
  ✓ All atoms synchronized!

After changing atom2:
  atom1: CHANGED_FROM_ATOM2
  atom2: CHANGED_FROM_ATOM2
  atom3: CHANGED_FROM_ATOM2
  ✓ Changes propagate bidirectionally!

============================================================
Example 2: Diodes (Unidirectional Binding)
============================================================
[... full output showing one-way data flow ...]

============================================================
Example 3: Circular Dependencies (No Infinite Loops!)
============================================================
[... circular dependency example ...]

============================================================
Example 4: Real-World Use Case - Form Synchronization
============================================================
[... form synchronization example ...]

============================================================
Example 5: Tree-Shaped Binding
============================================================
[... tree structure example ...]

============================================================
Key Concepts Summary
============================================================
✓ Conductors: Bidirectional (two-way) binding
✓ Diodes: Unidirectional (one-way) binding
✓ Capacitors: Reactive (notification) binding
✓ No infinite loops in circular dependencies
✓ Tree and complex graph structures supported
✓ Perfect for: Forms, State Management, Reactive UIs
============================================================
```

## Understanding the Syntax

### Underscore Positioning

The underscore indicates which atom will be returned for chaining:

- `atom1.diode_(atom2)` - Returns atom2 (underscore after)
- `atom1._diode(atom2)` - Returns atom1 (underscore before)

This allows flexible chaining:

```kotlin
// Both branch1 and branch2 connect to root
root._diode(branch1)._diode(branch2)

// Chain in sequence
atom1.diode_(atom2).diode_(atom3)
```

## Real-World Applications

### 1. Form Validation
```kotlin
val emailInput = Atom().with("")
val emailDisplay = Atom().with("")
val emailValid = Atom().with(false)

// Input flows to display
emailInput._diode(emailDisplay)

// Validation flows from input
emailInput._diode(emailValid)
```

### 2. State Management
```kotlin
val appState = Atom().with("IDLE")
val uiState = Atom().with("READY")
val loadingIndicator = Atom().with(false)

// State changes cascade
appState._diode(uiState)._diode(loadingIndicator)
```

### 3. Computed Properties
```kotlin
val firstName = Atom().with("")
val lastName = Atom().with("")
val fullName = Atom().with("")

// Full name updates when either changes
firstName._diode(fullName)
lastName._diode(fullName)
```

### 4. React-like State
```kotlin
class Component {
    val props = Atom().with(mapOf<String, Any>())
    val state = Atom().with(mapOf<String, Any>())
    val render = Atom().with("")
    
    init {
        // Re-render when props or state change
        props._diode(render)
        state._diode(render)
    }
}
```

## Comparison with Traditional Frameworks

### Traditional Approach (e.g., Vue, React, Angular)
```javascript
// Requires framework-specific syntax
@Watch('username')
onUsernameChange(newVal) {
    this.display = newVal;
    this.preview = newVal;
}

// Or using reactive systems
const username = ref('');
const display = computed(() => username.value);
```

### Virtual Physics Approach
```kotlin
// Simple, intuitive physics metaphor
username._diode(display)._diode(preview)
// That's it! No framework, no magic, just physics.
```

## Advantages

1. **No Framework Lock-in**: Pure Kotlin, works anywhere
2. **Type Safety**: Full compile-time type checking
3. **Predictable**: Physics-based rules, no magic
4. **No Performance Overhead**: Direct connections, no diffing
5. **Circular-Safe**: Handles circular dependencies automatically
6. **Flexible**: Combine conductors, diodes, and capacitors

## Next Steps

- Explore the rollback example for undo/redo functionality
- Check out the REST API example for data transmission
- Build a full UI with Virtual Physics data binding
- Integrate with Jetpack Compose or React

## Learn More

- Main documentation: [README.MD](../../README.MD)
- Getting started: [GETTING_STARTED.md](../../GETTING_STARTED.md)
- Source code: `src/main/kotlin/symmetrical/physics/`

## Questions?

Open an issue on GitHub or check the existing documentation.
