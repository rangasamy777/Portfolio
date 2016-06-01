//
//  RPNTape.swift
//  RPN Calculator
//
//  Created by Emile Bronkhorst on 29/02/2016.
//  Copyright © 2016 Envision Tech LLC. All rights reserved.
//

import UIKit

class RPNTape: UIViewController {

    @IBOutlet var tapeView:UITextView!
    
    /*
    * A 'copy' buffer to store the contents locally for easier access/object orientated practice
    */
    var buffer = Array<String>()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.loadContents()
    }

    /*
    * Fills the contents of the tapeView with the contents stored within the buffer
    */
    func loadContents(){
        for entry in buffer {
            tapeView.text = tapeView.text! + "\(entry)"
        }
    }
    
    /*
    * Clears the buffer, removing all values from the engines buffer also
    */
    @IBAction func clearBuffer(sender: UIButton){
        tapeView.text = ""
        engine.buffer.removeAll(keepCapacity: false)
    }
    
    /*
    * Returns to the Super view (the view that passed context to this view)
    */
    @IBAction func closeView(sender: UIButton){
        dismissViewControllerAnimated(true, completion: nil)
    }
}
