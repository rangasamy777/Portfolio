//
//  PrayerCell.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 07/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

class PrayerCell: UITableViewCell {

    @IBOutlet var name:UILabel!
    @IBOutlet var time:UILabel!
    @IBOutlet var standard:UILabel!
    
    func generateCell(prayer:Prayer){
        self.name.text = prayer.name
        self.time.text = "Time: \(prayer.time)"
        //self.standard.text = "Standard: East London Mosque"
    }
}
