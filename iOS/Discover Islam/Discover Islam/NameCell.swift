//
//  NameCell.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 15/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

class NameCell: UITableViewCell {

    @IBOutlet var arabic:UILabel!
    @IBOutlet var english:UILabel!
    @IBOutlet var meaning:UILabel!
    
    func generateCell(name: NameOfAllah){
        self.arabic.text = String(name.arabic)
        self.english.text = String(name.english)
        self.meaning.text = String(name.meaning)
    }

}
