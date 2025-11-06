# Virtual Physics API Reference

This directory will contain the complete API reference documentation for Virtual Physics.

## Current Status

API documentation is being developed. In the meantime, please refer to:

1. **Source Code**: The codebase is well-documented with KDoc comments
2. **Examples**: See the `examples/` directory for practical usage
3. **Getting Started**: Read [GETTING_STARTED.md](../../GETTING_STARTED.md)

## Planned Documentation

### Core Physics Concepts

- **Atoms** - Fundamental data building blocks
- **Molecules** - Small composite structures
- **Compounds** - Complex data structures
- **Elements** - Self-describing periodic table-based structures

### Serialization

- **emit()** - Serialize to photon strings
- **Absorber.materialize()** - Deserialize from photon strings

### Data Binding

- **Conductors** - Bidirectional binding
- **Diodes** - Unidirectional binding
- **Capacitors** - Reactive binding

### State Management

- **betaPlusDecay()** - Change value with history
- **betaMinusDecay()** - Rollback to previous value

### Chemistry

- **Polymers** - Collections of molecules
- **Filters** - Catalytic query operations

### Advanced Features

- **Gravity** - Variable scoping
- **Quantum** - Probabilistic states (future)
- **Thermodynamics** - Memory management (future)

## Generating API Documentation

To generate KDoc documentation:

```bash
./gradlew dokkaHtml
```

The generated documentation will be in `build/dokka/html/`.

## Contributing to API Docs

When adding new classes or methods, please:

1. Add comprehensive KDoc comments
2. Include usage examples in comments
3. Document parameters and return values
4. Link to related physics concepts (Wikipedia) when applicable

### Example KDoc Format

```kotlin
/**
 * Represents a fundamental data atom in Virtual Physics.
 * 
 * Atoms are the smallest units of data, inspired by atomic physics.
 * They can store a value and maintain a history for rollback operations.
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Atom">Atom (Wikipedia)</a>
 * 
 * Example usage:
 * ```kotlin
 * val atom = Atom().with("Initial Value")
 * atom.betaPlusDecay("New Value")  // Change with history
 * atom.betaMinusDecay()            // Rollback
 * ```
 */
class Atom {
    /**
     * Changes the atom's value and stores the previous value as a neutron.
     * 
     * This mimics beta plus decay in nuclear physics, where a proton
     * is converted to a neutron.
     * 
     * @param newValue The new value to set
     * @return The atom instance for chaining
     */
    fun betaPlusDecay(newValue: Any): Atom
}
```

## Quick Reference

### Core Classes

| Class | Package | Description |
|-------|---------|-------------|
| `Atom` | `symmetrical.physics.atomic.atoms` | Fundamental data unit |
| `Molecule` | `symmetrical.physics.atomic.substance.molecules` | Composite of atoms |
| `Compound` | `symmetrical.physics.atomic.substance.ions` | Complex structure |
| `Absorber` | `symmetrical.physics.subatomic.luminescent` | Deserialization |
| `Polymer` | `symmetrical.physics.atomic.substance.polymers` | Collection structure |
| `Filter` | `symmetrical.physics.atomic.catalysts` | Query operations |

### Common Operations

| Operation | Method | Description |
|-----------|--------|-------------|
| Serialize | `object.emit()` | Convert to photon string |
| Deserialize | `Absorber.materialize(photon)` | Reconstruct object |
| Change Value | `atom.betaPlusDecay(value)` | Set new value with history |
| Rollback | `atom.betaMinusDecay()` | Restore previous value |
| Bind (2-way) | `atom1.conductor_(atom2)` | Bidirectional binding |
| Bind (1-way) | `atom1.diode_(atom2)` | Unidirectional binding |
| Reactive | `atom1.capacitor_(atom2)` | Notification binding |

## Need Help?

- Check the [examples](../../examples/)
- Read the [Getting Started Guide](../../GETTING_STARTED.md)
- Review the [source code](../../src/main/kotlin/)
- Open an issue on GitHub

## Coming Soon

- Complete API reference for all packages
- Interactive API browser
- Code examples for each class and method
- Tutorial videos
- Comparison with similar frameworks
