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

import symmetrical.physics.atomic.atoms.Atom
import symmetrical.physics.atomic.substance.molecules.Molecule
import symmetrical.physics.atomic.substance.ions.Compound

/**
 * Debugger integration utilities for Virtual Physics.
 * Provides formatted views of particle states for IDE debugger windows.
 */
class DebuggerIntegration {
    
    /**
     * Formats an Atom for debugger display.
     */
    fun formatAtomForDebugger(atom: Atom): DebuggerView {
        val properties = mutableMapOf<String, String>()
        
        try {
            properties["Type"] = "Atom"
            properties["Class"] = atom::class.simpleName ?: "Unknown"
            properties["Value"] = atom.getContent()?.toString() ?: "null"
            
            val protonCount = try {
                atom.getProtons().getAtomicNumber()
            } catch (e: Exception) {
                0
            }
            properties["Protons"] = protonCount.toString()
            
            val neutronCount = try {
                atom.getNeutrons().size()
            } catch (e: Exception) {
                0
            }
            properties["Neutrons"] = neutronCount.toString()
            
            // Electron count is not directly accessible via public API
            properties["Electrons"] = "N/A"
            properties["Atomic Number"] = protonCount.toString()
        } catch (e: Exception) {
            properties["Error"] = e.message ?: "Unknown error"
        }
        
        val children = mutableListOf<DebuggerView>()
        
        // Add protons as children if accessible
        try {
            val protons = atom.getProtons()
            val atomicNumber = protons.getAtomicNumber()
            for (i in 0 until atomicNumber) {
                val proton = protons.get(i)
                children.add(DebuggerView(
                    name = "Proton[$i]",
                    value = proton.toString(),
                    type = "Proton",
                    properties = mapOf(
                        "Class" to (proton::class.simpleName ?: "Unknown")
                    )
                ))
            }
        } catch (e: Exception) {
            // Protons may not be accessible
        }
        
        return DebuggerView(
            name = "Atom",
            value = atom.getContent()?.toString() ?: "null",
            type = atom::class.simpleName ?: "Atom",
            properties = properties,
            children = children
        )
    }
    
    /**
     * Formats a Molecule for debugger display.
     */
    fun formatMoleculeForDebugger(molecule: Molecule): DebuggerView {
        val properties = mutableMapOf<String, String>()
        
        try {
            properties["Type"] = "Molecule"
            properties["Class"] = molecule::class.simpleName ?: "Unknown"
        } catch (e: Exception) {
            properties["Error"] = e.message ?: "Unknown error"
        }
        
        val children = mutableListOf<DebuggerView>()
        
        // Add atoms as children using ParticleBeam interface
        try {
            val atomCount = molecule.size()
            properties["Atom Count"] = atomCount.toString()
            
            for (i in 0 until atomCount) {
                val particle = molecule.get(i)
                val atom = particle as? Atom
                if (atom != null) {
                    children.add(formatAtomForDebugger(atom))
                }
            }
        } catch (e: Exception) {
            properties["Error accessing atoms"] = e.message ?: "Unknown"
        }
        
        return DebuggerView(
            name = "Molecule",
            value = "${molecule::class.simpleName} with ${children.size} atoms",
            type = molecule::class.simpleName ?: "Molecule",
            properties = properties,
            children = children
        )
    }
    
    /**
     * Formats a Compound for debugger display.
     */
    fun formatCompoundForDebugger(compound: Compound): DebuggerView {
        val properties = mutableMapOf<String, String>()
        
        properties["Type"] = "Compound"
        properties["Class"] = compound::class.simpleName ?: "Unknown"
        
        val children = mutableListOf<DebuggerView>()
        
        return DebuggerView(
            name = "Compound",
            value = compound::class.simpleName ?: "Unknown Compound",
            type = compound::class.simpleName ?: "Compound",
            properties = properties,
            children = children
        )
    }
    
    /**
     * Generates a detailed state snapshot for debugging.
     */
    fun generateStateSnapshot(atom: Atom): String {
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
        
        return buildString {
            append("═══════════════════════════════════════\n")
            append("ATOM STATE SNAPSHOT\n")
            append("═══════════════════════════════════════\n")
            append("Class: ${atom::class.qualifiedName}\n")
            append("Value: ${atom.getContent()}\n")
            append("Protons: $protonCount\n")
            append("Neutrons: $neutronCount\n")
            append("Atomic Number: $protonCount\n")
            append("═══════════════════════════════════════\n")
            
            // Electronics/Bindings section
            append("\nBINDINGS:\n")
            try {
                append("  (Detailed binding information requires runtime access)\n")
            } catch (e: Exception) {
                append("  Unable to access bindings: ${e.message}\n")
            }
            
            append("═══════════════════════════════════════\n")
        }
    }
    
    /**
     * Generates a watch expression helper.
     */
    fun generateWatchExpressions(variableName: String): List<WatchExpression> {
        return listOf(
            WatchExpression(
                expression = "$variableName.getContent()",
                description = "Current value"
            ),
            WatchExpression(
                expression = "$variableName.getProtons().getAtomicNumber()",
                description = "Number of protons (atomic number)"
            ),
            WatchExpression(
                expression = "$variableName.getNeutrons().size()",
                description = "Number of neutrons (rollback states)"
            ),
            WatchExpression(
                expression = "$variableName.emit().radiate()",
                description = "Serialized photon string"
            ),
            WatchExpression(
                expression = "$variableName.getValueProton()",
                description = "Value proton"
            )
        )
    }
    
    /**
     * Provides quick evaluation expressions for common debugging scenarios.
     */
    fun getQuickEvaluationExpressions(): Map<String, String> {
        return mapOf(
            "Get Value" to ".getContent()",
            "Serialize" to ".emit().radiate()",
            "Proton Count" to ".getProtons().getAtomicNumber()",
            "Has Neutrons (Rollback)" to ".getNeutrons().size() > 0",
            "Atomic Number" to ".getProtons().getAtomicNumber()",
            "Get Value Proton" to ".getValueProton()",
            "Format Value" to ".format()"
        )
    }
}

/**
 * Represents a debugger view of a Virtual Physics object.
 */
data class DebuggerView(
    val name: String,
    val value: String,
    val type: String,
    val properties: Map<String, String> = emptyMap(),
    val children: List<DebuggerView> = emptyList()
) {
    /**
     * Converts to a tree structure string for display.
     */
    fun toTreeString(indent: Int = 0): String {
        val prefix = "  ".repeat(indent)
        return buildString {
            append("$prefix$name: $value ($type)\n")
            
            if (properties.isNotEmpty()) {
                properties.forEach { (key, value) ->
                    append("$prefix  ├─ $key: $value\n")
                }
            }
            
            children.forEach { child ->
                append(child.toTreeString(indent + 1))
            }
        }
    }
}

/**
 * Represents a watch expression for debugging.
 */
data class WatchExpression(
    val expression: String,
    val description: String
)
