/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

/**
 * A type of transmission supported by a given transmission device.
 * 
 * @author Modular Robots @ MMMI
 */

public enum TransmissionType {
    RADIO, IR, WIRE_MALE, WIRE_FEMALE, WIRE_UNISEX
}