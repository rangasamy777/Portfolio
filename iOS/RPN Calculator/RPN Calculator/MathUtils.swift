//
//  MathUtils.swift
//  RPN Calculator
//
//  Created by Emile Bronkhorst on 29/02/2016.
//  Copyright © 2016 Envision Tech LLC. All rights reserved.
//

import UIKit

var Math:MathUtils = MathUtils()

class MathUtils: NSObject {
    
    /*
    * Alternatively, you can use: NSString(format: "%f.8", M_PI).doubleValue
    * This method was used for the sake of following the specification to the T
    */
    var piValue:Double = 3.14159265
    
    /*
    * Formats the number parse by dropping the first digit in the string parsed
    */
    func formatNumber(buffer: String) -> String {
        return String(buffer.characters.dropFirst())
    }

    /*
    * Returns the sine function to the value parsed
    * This method checks whether the mode is in Degrees/Radians
    */
    func sine(val:Double, mode: String) -> Double {
        return (isRadians(mode) ? sin(val) : sin((val * piValue) / 180))
    }
    
    /*
    * Returns the cosine function to the value parsed
    * This method checks whether the mode is in Degrees/Radians
    */
    func cosine(val : Double, mode:String) -> Double {
        return (isRadians(mode) ? cos(val) : cos((val * piValue) / 180))
    }
    
    /*
    * Returns the tangent function to the value parsed
    * This method checks whether the mode is in Degrees/Radians
    */
    func tangent(val: Double, mode: String) -> Double {
        return (isRadians(mode) ? tan(val) : tan((val * piValue) / 180))
    }
    
    /*
    * Returns the inverse sine function to the value parsed
    * This method checks whether the mode is in Degrees/Radians
    */
    func arcSine(val: Double, mode: String) -> Double {
        return (isRadians(mode) ? asin(val) : (asin(val) * 180) / piValue)
    }
    
    /*
    * Returns the inverse cosine function to the value parsed
    * This method checks whether the mode is in Degrees/Radians
    */
    func arcCos(val: Double, mode: String) -> Double {
        return (isRadians(mode) ? acos(val) : ((acos(val) * 180) / piValue))
    }
    
    /*
    * Returns the inverse tangent function to the value parsed
    * This method checks whether the mode is in Degrees/Radians
    */
    func arcTan(val: Double, mode: String) -> Double {
        return (isRadians(mode) ? atan(val) : ((atan(val) * 180) / piValue))
    }
    
    /*
    * Returns the value of the addition between valOne, valTwo
    */
    func add(valOne: Double, valTwo:Double) -> Double {
        return valOne + valTwo
    }
    
    /*
    * Returns the value of subtraction of valOne minus valTwo
    */
    func subtract(valOne: Double, valTwo: Double) -> Double {
        return valOne - valTwo
    }
    
    /*
    * Returns the value of the multiplication product of valOne multiplied by valTwo
    */
    func multiply(valOne: Double, valTwo: Double) -> Double {
        return valOne * valTwo
    }
    
    /*
    * Returns the value of valOne divided by valueTwo
    */
    func divide(valOne: Double, valTwo: Double) -> Double {
        return valOne / valTwo
    }
    
    /*
    * Returns the reciprocal of the value parsed
    */
    func reciprocal(val: Double) -> Double {
        return 1 / val
    }
    
    /*
    * Returns the value of value parsed multiplied by itself (Squared)
    */
    func square(val : Double) -> Double {
        return val * val
    }
    
    /*
    * Returns the value of Square Root of the value parsed
    */
    func squareRt(val: Double) -> Double {
        return sqrt(val)
    }
    
    /*
    * Returns the value of the logarithm base 10 (log10) of the value parsed
    */
    func logN(val: Double) -> Double {
        return log10(val)
    }
    
    /*
     * Returns the value of the Natural logarithm (lne) of the value parsed
    */
    func logE(val: Double) -> Double {
        return log(val)
    }
    
    /*
     * This method will return whether the calculator is in RADIANS mode or not
    */
    func isRadians(mode: String) -> Bool {
        return mode == "RAD";
    }
}
