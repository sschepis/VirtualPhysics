package symmetrical.tooling.examples

/*
 * This file is part of Virtual Physics.
 *
 * Copyright (C) [2024] Stephen R. DeSofi AKA Eschelon Azha
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import symmetrical.tooling.codegen.AtomGenerator
import symmetrical.tooling.codegen.MoleculeGenerator
import symmetrical.tooling.inspector.ParticleInspector
import symmetrical.tooling.migration.SchemaMigrator
import symmetrical.tooling.ide.*
import symmetrical.physics.atomic.atoms.Atom

/**
 * Example data class to be converted to Virtual Physics
 */
data class User(
    val name: String,
    val email: String,
    val age: Int,
    val active: Boolean
)

/**
 * Comprehensive examples demonstrating all developer tools.
 */
class DeveloperToolsExamples {
    
    /**
     * Example 1: Code Generation
     */
    fun codeGenerationExample() {
        println("╔═══════════════════════════════════════════════════════╗")
        println("║         Example 1: Code Generation                    ║")
        println("╚═══════════════════════════════════════════════════════╝\n")
        
        val atomGenerator = AtomGenerator()
        val moleculeGenerator = MoleculeGenerator()
        
        // Generate Atom for the User class
        println("// Generated Atom:")
        val atomCode = atomGenerator.generateAtom(User::class, "example.atoms")
        println(atomCode)
        println()
        
        // Generate Atoms for each property
        println("// Generated Atoms for each property:")
        val propertyAtoms = atomGenerator.generateAtomsFromProperties(User::class, "example.atoms")
        propertyAtoms.forEach { (name, code) ->
            println("// === $name ===")
            println(code)
            println()
        }
        
        // Generate Molecule for the User class
        println("// Generated Molecule:")
        val moleculeCode = moleculeGenerator.generateMolecule(User::class, "example.molecules")
        println(moleculeCode)
        println()
        
        // Generate usage example
        println("// Usage Example:")
        val usageExample = atomGenerator.generateUsageExample(User::class)
        println(usageExample)
        println()
    }
    
    /**
     * Example 2: Runtime Inspection
     */
    fun inspectionExample() {
        println("╔═══════════════════════════════════════════════════════╗")
        println("║         Example 2: Runtime Inspection                 ║")
        println("╚═══════════════════════════════════════════════════════╝\n")
        
        val inspector = ParticleInspector()
        
        // Create and inspect an Atom
        val atom = Atom().with("John Doe")
        val state = inspector.inspectAtom(atom)
        
        println("Inspected Atom State:")
        println("  Type: ${state.type}")
        println("  Class: ${state.className}")
        println("  Value: ${state.value}")
        println("  Protons: ${state.protonCount}")
        println("  Neutrons: ${state.neutronCount}")
        println("  Electrons: ${state.electronCount}")
        println()
        
        // Visualize the atom
        println("Visual Representation:")
        val visualization = inspector.visualizeParticle(state)
        println(visualization)
        println()
        
        // Track state changes
        println("Tracking state changes...")
        val tracker = inspector.trackStateChanges(atom) { newState ->
            println("  State changed! New value: ${newState.value}")
        }
        
        // Make a change
        atom.with("Jane Doe")
        tracker.checkForChanges()
        
        tracker.stop()
        println()
    }
    
    /**
     * Example 3: Schema Migration
     */
    fun migrationExample() {
        println("╔═══════════════════════════════════════════════════════╗")
        println("║         Example 3: Schema Migration                   ║")
        println("╚═══════════════════════════════════════════════════════╝\n")
        
        val migrator = SchemaMigrator()
        
        // Create a schema
        val schema = migrator.createSchema(
            "User",
            "name" to "string",
            "email" to "string",
            "age" to "integer",
            "active" to "boolean"
        )
        
        // Generate migration report
        println("Migration Report:")
        val report = migrator.generateMigrationReport(schema)
        println(report)
        println()
        
        // Generate Atoms from schema
        println("Generated Atoms:")
        val atoms = migrator.generateAtomsFromSchema(schema, "example.migrated.atoms")
        atoms.forEach { (name, code) ->
            println("// === $name ===")
            println(code.lines().take(15).joinToString("\n"))
            println("// ... (truncated)")
            println()
        }
        
        // Generate Molecule from schema
        println("Generated Molecule:")
        val molecule = migrator.generateMoleculeFromSchema(schema, "example.migrated.molecules")
        println(molecule.lines().take(25).joinToString("\n"))
        println("// ... (truncated)")
        println()
    }
    
    /**
     * Example 4: IDE Utilities - Templates
     */
    fun templateExample() {
        println("╔═══════════════════════════════════════════════════════╗")
        println("║         Example 4: Code Templates                     ║")
        println("╚═══════════════════════════════════════════════════════╝\n")
        
        // Show all available templates
        println("Available Templates:")
        CodeTemplates.getAllTemplates().forEach { (name, template) ->
            println("  • $name")
        }
        println()
        
        // Show specific templates
        println("Atom Template:")
        println(CodeTemplates.ATOM_TEMPLATE)
        println()
        
        println("Conductor Binding Template:")
        println(CodeTemplates.CONDUCTOR_BINDING_TEMPLATE)
        println()
    }
    
    /**
     * Example 5: Syntax Highlighting
     */
    fun syntaxHighlightingExample() {
        println("╔═══════════════════════════════════════════════════════╗")
        println("║         Example 5: Syntax Highlighting                ║")
        println("╚═══════════════════════════════════════════════════════╝\n")
        
        println("Physics Keywords:")
        SyntaxHighlighting.PHYSICS_KEYWORDS.take(10).forEach { keyword ->
            println("  • $keyword")
        }
        println("  ... and ${SyntaxHighlighting.PHYSICS_KEYWORDS.size - 10} more")
        println()
        
        println("Keyword Categories:")
        SyntaxHighlighting.getKeywordCategories().forEach { (category, keywords) ->
            println("  $category: ${keywords.joinToString(", ")}")
        }
        println()
    }
    
    /**
     * Example 6: Circuit Designer
     */
    fun circuitDesignerExample() {
        println("╔═══════════════════════════════════════════════════════╗")
        println("║         Example 6: Circuit Designer                   ║")
        println("╚═══════════════════════════════════════════════════════╝\n")
        
        val designer = CircuitDesigner()
        
        // Create a circuit
        val circuit = Circuit(
            nodes = listOf(
                CircuitNode("userNameAtom", "atom", "User Name", 0.0, 0.0),
                CircuitNode("displayNameAtom", "atom", "Display Name", 100.0, 0.0),
                CircuitNode("emailAtom", "atom", "Email", 0.0, 100.0),
                CircuitNode("backupEmailAtom", "atom", "Backup Email", 100.0, 100.0)
            ),
            connections = listOf(
                CircuitConnection("userNameAtom", "displayNameAtom", "conductor", "both"),
                CircuitConnection("emailAtom", "backupEmailAtom", "diode", "forward")
            )
        )
        
        // Validate circuit
        println("Validating circuit...")
        val validation = designer.validateCircuit(circuit)
        println("  Valid: ${validation.isValid}")
        if (validation.errors.isNotEmpty()) {
            println("  Errors:")
            validation.errors.forEach { println("    • $it") }
        }
        if (validation.warnings.isNotEmpty()) {
            println("  Warnings:")
            validation.warnings.forEach { println("    • $it") }
        }
        println()
        
        // Generate code
        println("Generated Code from Circuit:")
        val code = designer.generateCodeFromCircuit(circuit)
        println(code)
        println()
    }
    
    /**
     * Example 7: Debugger Integration
     */
    fun debuggerIntegrationExample() {
        println("╔═══════════════════════════════════════════════════════╗")
        println("║         Example 7: Debugger Integration               ║")
        println("╚═══════════════════════════════════════════════════════╝\n")
        
        val debugger = DebuggerIntegration()
        val atom = Atom().with("Debug Value")
        
        // Format for debugger
        println("Debugger View:")
        val view = debugger.formatAtomForDebugger(atom)
        println(view.toTreeString())
        println()
        
        // State snapshot
        println("State Snapshot:")
        val snapshot = debugger.generateStateSnapshot(atom)
        println(snapshot)
        println()
        
        // Watch expressions
        println("Suggested Watch Expressions:")
        val watches = debugger.generateWatchExpressions("myAtom")
        watches.forEach { watch ->
            println("  ${watch.description.padEnd(35)}: ${watch.expression}")
        }
        println()
        
        // Quick evaluations
        println("Quick Evaluation Expressions:")
        val quickEvals = debugger.getQuickEvaluationExpressions()
        quickEvals.forEach { (name, expr) ->
            println("  ${name.padEnd(25)}: myAtom$expr")
        }
        println()
    }
    
    /**
     * Run all examples
     */
    fun runAll() {
        codeGenerationExample()
        inspectionExample()
        migrationExample()
        templateExample()
        syntaxHighlightingExample()
        circuitDesignerExample()
        debuggerIntegrationExample()
    }
}

/**
 * Main function to run the examples
 */
fun main() {
    println("═══════════════════════════════════════════════════════════")
    println("       Virtual Physics Developer Tools Examples")
    println("═══════════════════════════════════════════════════════════")
    println()
    
    val examples = DeveloperToolsExamples()
    examples.runAll()
    
    println("═══════════════════════════════════════════════════════════")
    println("                    Examples Complete!")
    println("═══════════════════════════════════════════════════════════")
}
