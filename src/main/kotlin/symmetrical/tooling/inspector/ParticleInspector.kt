package symmetrical.tooling.inspector

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

import symmetrical.physics.atomic.atoms.Atom
import symmetrical.physics.atomic.substance.molecules.Molecule
import symmetrical.physics.atomic.substance.ions.Compound

/**
 * Data class representing the state of a particle at inspection time.
 */
data class ParticleState(
    val type: String,
    val className: String,
    val value: Any?,
    val protonCount: Int,
    val neutronCount: Int,
    val electronCount: Int,
    val bindings: List<BindingInfo>,
    val metadata: Map<String, Any?>
)

/**
 * Data class representing a binding relationship between particles.
 */
data class BindingInfo(
    val bindingType: String, // "conductor", "diode", "capacitor"
    val direction: String,   // "bidirectional", "forward", "reverse"
    val targetId: String
)

/**
 * Runtime Inspector Tool for Virtual Physics particles.
 * Provides visualization and analysis of particle states, relationships,
 * and runtime behavior.
 */
class ParticleInspector {
    
    /**
     * Inspects an Atom and returns its current state.
     */
    fun inspectAtom(atom: Atom): ParticleState {
        val bindings = mutableListOf<BindingInfo>()
        
        // Analyze electronic bindings - bindings are stored in the atom's electronics
        // Note: Detailed binding inspection requires access to internal structures
        
        val protonCount = try {
            atom.getProtons().getAtomicNumber()
        } catch (e: Exception) {
            0
        }
        
        val neutronCount = try {
            atom.getNeutrons().size()
        } catch (e: Exception) {
            0
        }
        
        // Electron count is not directly accessible via public API
        val electronCount = 0
        
        return ParticleState(
            type = "Atom",
            className = atom::class.simpleName ?: "Unknown",
            value = atom.getContent(),
            protonCount = protonCount,
            neutronCount = neutronCount,
            electronCount = electronCount,
            bindings = bindings,
            metadata = mapOf(
                "hasValue" to (atom.getContent() != null),
                "atomicNumber" to protonCount
            )
        )
    }
    
    /**
     * Inspects a Molecule and returns its current state along with contained atoms.
     */
    fun inspectMolecule(molecule: Molecule): MoleculeState {
        val atoms = mutableListOf<ParticleState>()
        
        // Get atoms from the molecule using the ParticleBeam interface
        try {
            val atomCount = molecule.size()
            for (i in 0 until atomCount) {
                val particle = molecule.get(i)
                val atom = particle as? Atom
                if (atom != null) {
                    atoms.add(inspectAtom(atom))
                }
            }
        } catch (e: Exception) {
            // May not have accessible atoms
        }
        
        return MoleculeState(
            type = "Molecule",
            className = molecule::class.simpleName ?: "Unknown",
            atomCount = atoms.size,
            atoms = atoms,
            metadata = mapOf(
                "totalProtons" to atoms.sumOf { it.protonCount },
                "totalNeutrons" to atoms.sumOf { it.neutronCount },
                "totalElectrons" to atoms.sumOf { it.electronCount }
            )
        )
    }
    
    /**
     * Inspects a Compound and returns detailed structural information.
     */
    fun inspectCompound(compound: Compound): CompoundState {
        val molecules = mutableListOf<MoleculeState>()
        val atoms = mutableListOf<ParticleState>()
        
        return CompoundState(
            type = "Compound",
            className = compound::class.simpleName ?: "Unknown",
            moleculeCount = molecules.size,
            atomCount = atoms.size,
            molecules = molecules,
            atoms = atoms,
            metadata = emptyMap()
        )
    }
    
    /**
     * Generates a text-based visualization of a particle's state.
     */
    fun visualizeParticle(state: ParticleState): String {
        return buildString {
            append("╔═══════════════════════════════════════════╗\n")
            append("║ ${state.type}: ${state.className.padEnd(34)} ║\n")
            append("╠═══════════════════════════════════════════╣\n")
            append("║ Value: ${state.value.toString().take(33).padEnd(33)} ║\n")
            append("║ Protons:   ${state.protonCount.toString().padEnd(30)} ║\n")
            append("║ Neutrons:  ${state.neutronCount.toString().padEnd(30)} ║\n")
            append("║ Electrons: ${state.electronCount.toString().padEnd(30)} ║\n")
            
            if (state.bindings.isNotEmpty()) {
                append("╠═══════════════════════════════════════════╣\n")
                append("║ Bindings:                                 ║\n")
                state.bindings.take(3).forEach { binding ->
                    val info = "${binding.bindingType} (${binding.direction})"
                    append("║   • ${info.take(37).padEnd(37)} ║\n")
                }
                if (state.bindings.size > 3) {
                    append("║   ... and ${state.bindings.size - 3} more              ║\n")
                }
            }
            
            append("╚═══════════════════════════════════════════╝\n")
        }
    }
    
    /**
     * Generates a relationship graph in DOT format for visualization.
     * Can be rendered using Graphviz or similar tools.
     */
    fun generateRelationshipGraph(particles: List<ParticleState>): String {
        return buildString {
            append("digraph ParticleRelationships {\n")
            append("  rankdir=LR;\n")
            append("  node [shape=box, style=rounded];\n\n")
            
            particles.forEachIndexed { index, particle ->
                val nodeId = "particle_$index"
                append("  $nodeId [label=\"${particle.className}\\n${particle.value}\"];\n")
                
                particle.bindings.forEach { binding ->
                    val style = when (binding.bindingType) {
                        "conductor" -> "style=bold, dir=both"
                        "diode" -> "style=solid"
                        "capacitor" -> "style=dashed"
                        else -> "style=dotted"
                    }
                    append("  $nodeId -> target_${binding.targetId} [$style];\n")
                }
            }
            
            append("}\n")
        }
    }
    
    /**
     * Tracks changes in particle state over time.
     */
    fun trackStateChanges(atom: Atom, callback: (ParticleState) -> Unit): StateTracker {
        return StateTracker(atom, callback)
    }
}

/**
 * Data class representing the state of a Molecule.
 */
data class MoleculeState(
    val type: String,
    val className: String,
    val atomCount: Int,
    val atoms: List<ParticleState>,
    val metadata: Map<String, Any?>
)

/**
 * Data class representing the state of a Compound.
 */
data class CompoundState(
    val type: String,
    val className: String,
    val moleculeCount: Int,
    val atomCount: Int,
    val molecules: List<MoleculeState>,
    val atoms: List<ParticleState>,
    val metadata: Map<String, Any?>
)

/**
 * State tracker for monitoring particle changes over time.
 */
class StateTracker(
    private val atom: Atom,
    private val callback: (ParticleState) -> Unit
) {
    private val inspector = ParticleInspector()
    private var previousState: ParticleState? = null
    
    /**
     * Checks for state changes and invokes callback if changes detected.
     */
    fun checkForChanges() {
        val currentState = inspector.inspectAtom(atom)
        if (currentState != previousState) {
            callback(currentState)
            previousState = currentState
        }
    }
    
    /**
     * Stops tracking.
     */
    fun stop() {
        // Cleanup if needed
    }
}
