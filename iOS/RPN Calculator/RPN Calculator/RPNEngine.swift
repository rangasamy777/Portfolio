//
//  RPNEngine.swift
//  RPN Calculator
//
//  Created by Emile Bronkhorst on 29/02/2016.
//  Copyright © 2016 Envision Tech LLC. All rights reserved.
//

import UIKit

/*
* Creates a Global instance to be used across any classes within the target by calling the object name
*/
var engine:RPNEngine = RPNEngine()

class RPNEngine: NSObject {
    
    /*
    * Contains the values of the buffer
    */
    var operandStack = Array<Double>()
    var buffer = Array<String>()
    
    /*
    * Pushes the values onto the operand buffer, whilst updating the label with the contents
    */
    func pushOperand(operand: Double, buffer: UILabel){
        self.operandStack.append(operand)
        self.buffer.append("\(operand) ⏎")
        buffer.text = buffer.text! + "\(operand) ⏎"
    }
    
    /*
    * Performs the main unary functions on the operands within the buffer
    */
    func operate(operation: String, mode: String) -> Double {
        if(operandStack.count >= 2){
            self.buffer.append(operation + "\n")
            switch operation {
            case "+":
                return Math.add(operandStack.removeLast(), valTwo: operandStack.removeLast())
            case "−":
                return Math.subtract(operandStack.removeFirst(), valTwo: operandStack.removeLast())
            case "÷":
                return Math.divide(operandStack.removeFirst(), valTwo: operandStack.removeLast())
            case "×":
                return Math.multiply(operandStack.removeLast(), valTwo: operandStack.removeLast())
            default:
                break
            }
        }
        return 0.0
    }
    
    /*
    * Performs the trignometric functions on the value parsed
    */
    func specialFunctions(function: String, mode: String) -> Double {
        self.buffer.append(function + "\n")
        let value = operandStack.removeLast()
        switch function {
        case "cos":
            return format(Math.cosine(value, mode: mode))
        case "tan":
            return format(Math.tangent(value, mode: mode))
        case "sin":
            return format(Math.sine(value, mode: mode))
        case "aSin":
            return format(Math.arcSine(value, mode: mode))
        case "aCos":
            return format(Math.arcCos(value, mode: mode))
        case "aTan":
            return format(Math.arcTan(value, mode: mode))
        case "ⅹ²":
            return format(Math.square(value))
        case "√":
            return format(Math.squareRt(value))
        case "logᵉ":
            return format(Math.logE(value))
        case "log10":
            return format(Math.logN(value))
        default:break
        }
        return 0.0
    }
    
    /*
     * Formats the number the parsed to 5 decimal places (where applicable) and returns the value as a Double
    */
    func format(value: Double) -> Double {
        return NSString(format: "%.05f", value).doubleValue
    }
}
