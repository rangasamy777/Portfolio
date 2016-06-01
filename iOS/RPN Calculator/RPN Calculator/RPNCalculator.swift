//
//  ViewController.swift
//  RPN Calculator
//
//  Created by Emile Bronkhorst on 29/02/2016.
//  Copyright © 2016 Envision Tech LLC. All rights reserved.
//

import UIKit

class RPNCalculator: UIViewController {
    
    @IBOutlet var displayLbl: UILabel!
    @IBOutlet var bufferLbl: UILabel!
    @IBOutlet var tanBtn: UIButton!
    @IBOutlet var cosBtn: UIButton!
    @IBOutlet var sinBtn: UIButton!
    
    /*
    * Mode the calculator is in
    */
    var mode:String = "DEG"
    
    /*
    * After the special functions have updated the display, this boolean allows for the display to be reset
    */
    var clearDisplay = false
    
    /*
    * Grabs the value from the current display
    * Sets the value of the current display to the newly passed value
    * Displays an error message 'Not a number' if the result is not a number
    */
    var displayValue : Double {
        get {
            let number = (NSNumberFormatter().numberFromString(displayLbl.text!))?.doubleValue
            return (number != nil ? number! : 0.0)
        }
        set (updatedValue) {
            displayLbl.text = (isnan(updatedValue) ? "Not a number" : "\(updatedValue)")
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        bufferLbl.text = ""
        self.loadUserSettings()
    }
    
    /*
    * Checks if the value on the display has a preceeding 0, removes it if appropriate
    * Appends the digit pressed
    */
    @IBAction func appendDigit(sender: UIButton){
        let digit = sender.currentTitle!
        
        if(clearDisplay){
            displayLbl.text = "0"
            clearDisplay = false
        }
        
        if(displayLbl.text!.hasPrefix("0") && !displayLbl.text!.containsString(".")){
            let formatted = Math.formatNumber(displayLbl.text!)
            displayLbl.text = "\(formatted)"
        }
        
        displayLbl.text = displayLbl.text! + "\(digit)"
    }
    
    /*
    * Enter the current value on the display to the operand stack, displaying an error if there are already more than 2 values in the stack
    */
    @IBAction func enterValue(sender: UIButton){
        engine.pushOperand(displayValue, buffer: bufferLbl)
        displayLbl.text = "0"
    }
    
    /*
    * Performs the operation of the specified operator
    */
    @IBAction func appendOperation(sender: UIButton){
        let operation = sender.currentTitle!
        
        if(displayValue != 0 && engine.operandStack.count < 2){
            engine.pushOperand(displayValue, buffer: bufferLbl)
        }
        
        displayValue = engine.operate(operation, mode: mode)
        engine.pushOperand(displayValue, buffer: bufferLbl)
        updateBufferLabel(bufferLbl)
        clearDisplay = true
    }
    
    /*
    * Clears the buffer
    * Tag 0 - Clears the current display + last buffer entry
    * Tag 1 - Clears the whole buffer, operandstack and display
    */
    @IBAction func clearBuffer(sender: UIButton){
        let tag = sender.tag
        
        switch(tag){
        case 0:
            displayLbl.text = "0"
            bufferLbl.text = ""
            
            if(engine.operandStack.count > 0){engine.operandStack.removeLast()}
            if(engine.buffer.count > 0){engine.buffer.removeLast()}
            
            for entry in engine.operandStack {
                bufferLbl.text = bufferLbl.text! + "\(entry)⏎"
            }
            engine.buffer.append("C \n")
            break
        case 1:
            engine.operandStack.removeAll(keepCapacity: false)
            displayLbl.text = "0"
            bufferLbl.text = ""
            engine.buffer.append("AC \n")
            break
        default:break
        }
    }
    
    /*
    * Handles the special functions e.g. trigonometric functions
    * Updates the display (where neccessary) 
    * Pushes the value of the current operation onto the operand stack
    * Allows for the display to be cleared
    */
    @IBAction func specialFunctions(sender: UIButton){
        let function = sender.currentTitle!
        
        switch(function){
        case ".":
            !displayLbl.text!.containsString(".") ? displayLbl.text = displayLbl.text! + "." : ()
            clearDisplay = false
            return
        case "+/-":
            !displayLbl.text!.containsString("-") ? displayLbl.text!.insert("-", atIndex: displayLbl.text!.startIndex) : (displayLbl.text = displayLbl.text!.stringByReplacingOccurrencesOfString("-", withString: ""))
            return
        case "⬅︎":
            if(displayValue != 0){
                let str:String = displayLbl.text!
                displayLbl.text = (str.characters.count > 1 ? str.substringToIndex(displayLbl.text!.endIndex.predecessor()): "0")
            }
            clearDisplay = false
            return
        case "π":
            displayValue = Math.piValue
            break
        case "sin", "cos", "tan", "aCos", "aSin", "aTan", "ⅹ²", "√", "logᵉ", "log10":
            if(engine.operandStack.count > 0){
                displayValue = engine.specialFunctions(function, mode: mode)
            } else {
                displayError("There are no values on the operand stack.")
                return
            }
            break
        case "1/x":
            if(displayValue != 0){
                displayValue = Math.reciprocal(displayValue)
            } else {
                displayError("You cannot divide by 0")
                return
            }
            break
        default:break
        }
        
        engine.pushOperand(displayValue, buffer: bufferLbl)
        updateBufferLabel(bufferLbl)
        clearDisplay = true
    }
    
    /*
    * Allows swapping between the regular trignometric functions and their inverse functions
    */
    @IBAction func swapSecond(sender: UIButton){
        let tag = sender.tag
        switch(tag){
        case 0:
            sinBtn.setTitle("aSin", forState: .Normal)
            cosBtn.setTitle("aCos", forState: .Normal)
            tanBtn.setTitle("aTan", forState: .Normal)
            sender.tag = 1
            break
        default:
            sinBtn.setTitle("sin", forState: .Normal)
            cosBtn.setTitle("cos", forState: .Normal)
            tanBtn.setTitle("tan", forState: .Normal)
            sender.tag = 0
            break
        }
    }
    
    /*
    * Allows for change between Degrees/Radians mode
    */
    @IBAction func changeMode(sender: UIButton){
        switch (mode){
            case "DEG":
            self.mode = "RAD"
            break
        default:
            self.mode = "DEG"
            break
        }
        sender.setTitle(mode, forState: .Normal)
        print(mode)
    }
    
    /*
    * Called before the view controller passes onto the destination view controller
    * Instanciates the destination view controller
    * Sets the values appropriate for the buffer
    */
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        let tapeview:RPNTape = segue.destinationViewController as! RPNTape
        
        tapeview.modalTransitionStyle = UIModalTransitionStyle.FlipHorizontal
        tapeview.buffer = engine.buffer
    }
    
    /*
    * Displays an Error message with the content being set to the message parsed
    * Allows for a 'Dismiss' button to dismiss the alert
    */
    func displayError(msg: String){
        let alert:UIAlertController = UIAlertController(title: "Error", message: msg, preferredStyle: .Alert)
        
        let dismiss:UIAlertAction = UIAlertAction(title: "Dismiss", style: .Default, handler: nil)
        alert.addAction(dismiss)
        
        presentViewController(alert, animated: true, completion: nil)
    }
    
    /*
     * Updates the buffer label on the upper portion of the display label. 
     * Clears the contents of the label first
     * Iterates through the entries and appends them to the contents of the label
    */
    func updateBufferLabel(buffer: UILabel){
        bufferLbl.text = ""
        for entry in engine.operandStack{
            bufferLbl.text!.appendContentsOf("\(entry)⏎")
        }
    }
    
    /*
     * Loads the data which has been stored in the buffer (if applicable).
     * Sets the buffer of the application to the loaded buffer (if applicable)
    */
    func loadUserSettings(){
        let userSettings = NSUserDefaults.standardUserDefaults()
        if let buffer = userSettings.objectForKey("tapeBuffer") {
            print(buffer)
            engine.buffer = buffer as! Array<String>
        }
    }
}

