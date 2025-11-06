package symmetrical.tooling.ide

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

/**
 * Code completion templates for Virtual Physics structures.
 * These can be integrated into IDE plugins for IntelliJ IDEA.
 */
object CodeTemplates {
    
    /**
     * Template for creating a basic Atom.
     */
    val ATOM_TEMPLATE = """
        |class ${"$"}NAME${"$"}Atom(
        |    private val matterAntiMatter: IMatterAntiMatter = MatterAntiMatter().with(${"$"}NAME${"$"}Atom::class)
        |) : Hydrogen(), IMatterAntiMatter by matterAntiMatter {
        |    
        |    fun with(value: Any?): ${"$"}NAME${"$"}Atom {
        |        setAtomicValue(value, true)
        |        return this
        |    }
        |}
    """.trimMargin()
    
    /**
     * Template for creating a Molecule.
     */
    val MOLECULE_TEMPLATE = """
        |class ${"$"}NAME${"$"}Molecule(
        |    private val matterAntiMatter: IMatterAntiMatter = MatterAntiMatter().with(${"$"}NAME${"$"}Molecule::class)
        |) : Molecule(), IMatterAntiMatter by matterAntiMatter {
        |    
        |    private var ${"$"}FIELD${"$"}Atom: Atom = Atom()
        |    
        |    fun with(${"$"}FIELD${"$"}: Any?): ${"$"}NAME${"$"}Molecule {
        |        ${"$"}FIELD${"$"}Atom.with(${"$"}FIELD${"$"})
        |        bind(${"$"}FIELD${"$"}Atom)
        |        return this
        |    }
        |}
    """.trimMargin()
    
    /**
     * Template for creating a Compound.
     */
    val COMPOUND_TEMPLATE = """
        |class ${"$"}NAME${"$"}Compound(
        |    private val matterAntiMatter: IMatterAntiMatter = MatterAntiMatter().with(${"$"}NAME${"$"}Compound::class)
        |) : Compound(), IMatterAntiMatter by matterAntiMatter {
        |    
        |    // Add molecules and atoms here
        |}
    """.trimMargin()
    
    /**
     * Template for two-way data binding (conductor).
     */
    val CONDUCTOR_BINDING_TEMPLATE = """
        |// Two-way data binding with conductor
        |val atom1 = Atom().with("${"$"}VALUE1${"$"}")
        |val atom2 = Atom().with("${"$"}VALUE2${"$"}")
        |atom1.conductor(atom2)
    """.trimMargin()
    
    /**
     * Template for one-way data binding (diode).
     */
    val DIODE_BINDING_TEMPLATE = """
        |// One-way data binding with diode
        |val source = Atom().with("${"$"}SOURCE_VALUE${"$"}")
        |val target = Atom().with("${"$"}TARGET_VALUE${"$"}")
        |source.diode_(target)
    """.trimMargin()
    
    /**
     * Template for reactive binding (capacitor).
     */
    val CAPACITOR_BINDING_TEMPLATE = """
        |// Reactive data binding with capacitor
        |val atom1 = Atom().with("${"$"}VALUE1${"$"}")
        |val atom2 = Atom().with("${"$"}VALUE2${"$"}")
        |atom1.capacitor(atom2)
    """.trimMargin()
    
    /**
     * Template for serialization (emission).
     */
    val EMISSION_TEMPLATE = """
        |// Serialize particle to photon string
        |val emission = ${"$"}PARTICLE${"$"}.emit()
        |
        |// Deserialize photon string back to particle
        |val (clone, _) = Absorber.materialize(emission)
    """.trimMargin()
    
    /**
     * Template for rollback (beta decay).
     */
    val ROLLBACK_TEMPLATE = """
        |// Store current value and set new value
        |${"$"}ATOM${"$"}.betaPlusDecay("${"$"}NEW_VALUE${"$"}")
        |
        |// Rollback to previous value
        |${"$"}ATOM${"$"}.betaMinusDecay()
    """.trimMargin()
    
    /**
     * Template for polymer (collection).
     */
    val POLYMER_TEMPLATE = """
        |// Create a polymer (collection of molecules)
        |val polymer = Polymer()
        |polymer.bind(${"$"}MOLECULE1${"$"})
        |polymer.bind(${"$"}MOLECULE2${"$"})
        |
        |// Filter polymer
        |val filter = Filter().with(polymer)
        |    .where(${"$"}CONDITION${"$"})
        |val result = filter.activate()
    """.trimMargin()
    
    /**
     * Returns all templates as a map.
     */
    fun getAllTemplates(): Map<String, String> {
        return mapOf(
            "atom" to ATOM_TEMPLATE,
            "molecule" to MOLECULE_TEMPLATE,
            "compound" to COMPOUND_TEMPLATE,
            "conductor" to CONDUCTOR_BINDING_TEMPLATE,
            "diode" to DIODE_BINDING_TEMPLATE,
            "capacitor" to CAPACITOR_BINDING_TEMPLATE,
            "emission" to EMISSION_TEMPLATE,
            "rollback" to ROLLBACK_TEMPLATE,
            "polymer" to POLYMER_TEMPLATE
        )
    }
}

/**
 * Syntax highlighting keywords for Virtual Physics.
 * Can be used in IDE plugins for syntax highlighting configuration.
 */
object SyntaxHighlighting {
    
    /**
     * Physics-related keywords that should be highlighted.
     */
    val PHYSICS_KEYWORDS = setOf(
        // Particles
        "Atom", "Molecule", "Compound", "Particle",
        "Proton", "Neutron", "Electron",
        "Quark", "Lepton", "Boson",
        
        // Bonds and bindings
        "conductor", "diode", "capacitor",
        "conductor_", "_conductor",
        "diode_", "_diode",
        
        // Serialization
        "emit", "absorb", "materialize",
        "Photon", "Absorber",
        
        // Rollback
        "betaPlusDecay", "betaMinusDecay",
        
        // Chemistry
        "Polymer", "Monomer", "Filter", "Catalyst",
        
        // Elements
        "Hydrogen", "Helium", "Lithium",
        
        // Forces
        "Gravity", "Magnetism", "Strong", "Weak"
    )
    
    /**
     * Method names that should be highlighted specially.
     */
    val SPECIAL_METHODS = setOf(
        "with", "bind", "emit", "absorb",
        "getContent", "setAtomicValue",
        "getValueProton", "getAtomicNumber",
        "getProtonCount", "getNeutronCount", "getElectronCount"
    )
    
    /**
     * Returns keyword categories for syntax highlighting.
     */
    fun getKeywordCategories(): Map<String, Set<String>> {
        return mapOf(
            "physics_particles" to setOf("Atom", "Molecule", "Compound", "Particle"),
            "physics_bindings" to setOf("conductor", "diode", "capacitor"),
            "physics_serialization" to setOf("emit", "absorb", "materialize"),
            "physics_rollback" to setOf("betaPlusDecay", "betaMinusDecay"),
            "physics_chemistry" to setOf("Polymer", "Monomer", "Filter", "Catalyst")
        )
    }
}

/**
 * Circuit designer data model for visual data binding configuration.
 */
data class CircuitNode(
    val id: String,
    val type: String, // "atom", "molecule", "compound"
    val label: String,
    val x: Double,
    val y: Double
)

data class CircuitConnection(
    val sourceId: String,
    val targetId: String,
    val connectionType: String, // "conductor", "diode", "capacitor"
    val direction: String = "forward" // "forward", "reverse", "both"
)

data class Circuit(
    val nodes: List<CircuitNode>,
    val connections: List<CircuitConnection>
)

/**
 * Visual circuit designer for data binding.
 * Provides utilities to create and validate binding circuits.
 */
class CircuitDesigner {
    
    /**
     * Validates a circuit for correctness.
     */
    fun validateCircuit(circuit: Circuit): CircuitValidationResult {
        val errors = mutableListOf<String>()
        val warnings = mutableListOf<String>()
        
        // Check for orphaned nodes
        val connectedNodeIds = circuit.connections.flatMap { 
            listOf(it.sourceId, it.targetId) 
        }.toSet()
        
        val orphanedNodes = circuit.nodes.filter { it.id !in connectedNodeIds }
        if (orphanedNodes.isNotEmpty()) {
            warnings.add("Found ${orphanedNodes.size} orphaned node(s) with no connections")
        }
        
        // Check for circular dependencies with diodes
        val hasCircularDiodes = detectCircularDiodes(circuit)
        if (hasCircularDiodes) {
            warnings.add("Circular diode connections detected - ensure proper ordering")
        }
        
        // Check for invalid connection types
        circuit.connections.forEach { conn ->
            if (conn.connectionType !in setOf("conductor", "diode", "capacitor")) {
                errors.add("Invalid connection type: ${conn.connectionType}")
            }
        }
        
        return CircuitValidationResult(
            isValid = errors.isEmpty(),
            errors = errors,
            warnings = warnings
        )
    }
    
    /**
     * Generates Kotlin code from a circuit design.
     */
    fun generateCodeFromCircuit(circuit: Circuit): String {
        return buildString {
            append("// Generated from circuit designer\n\n")
            
            // Create nodes
            circuit.nodes.forEach { node ->
                append("val ${node.id} = ${node.type.replaceFirstChar { it.uppercase() }}()")
                append(".with(\"${node.label}\")\n")
            }
            append("\n")
            
            // Create connections
            circuit.connections.forEach { conn ->
                val method = when (conn.connectionType) {
                    "conductor" -> "conductor"
                    "diode" -> if (conn.direction == "forward") "diode_" else "_diode"
                    "capacitor" -> "capacitor"
                    else -> "conductor"
                }
                
                append("${conn.sourceId}.$method(${conn.targetId})\n")
            }
        }
    }
    
    /**
     * Detects circular dependencies in diode connections.
     */
    private fun detectCircularDiodes(circuit: Circuit): Boolean {
        val diodeConnections = circuit.connections.filter { it.connectionType == "diode" }
        val graph = diodeConnections.groupBy { it.sourceId }
        
        val visited = mutableSetOf<String>()
        val recursionStack = mutableSetOf<String>()
        
        fun hasCycle(nodeId: String): Boolean {
            visited.add(nodeId)
            recursionStack.add(nodeId)
            
            graph[nodeId]?.forEach { connection ->
                val targetId = connection.targetId
                if (targetId !in visited) {
                    if (hasCycle(targetId)) return true
                } else if (targetId in recursionStack) {
                    return true
                }
            }
            
            recursionStack.remove(nodeId)
            return false
        }
        
        circuit.nodes.forEach { node ->
            if (node.id !in visited) {
                if (hasCycle(node.id)) return true
            }
        }
        
        return false
    }
}

/**
 * Result of circuit validation.
 */
data class CircuitValidationResult(
    val isValid: Boolean,
    val errors: List<String>,
    val warnings: List<String>
)
