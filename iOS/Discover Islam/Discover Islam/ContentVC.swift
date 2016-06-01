//
//  ContentVC.swift
//  Discover Islam
//
//  Created by Emile Bronkhorst on 17/07/2015.
//  Copyright (c) 2015 Envision Tech LLC. All rights reserved.
//

import UIKit

class ContentVC: UIViewController {

    @IBOutlet var titleLbl:UILabel!
    @IBOutlet var imgView:UIImageView!
    
    var pageIndex:Int!
    var pageTitle:String!
    var pageImg:String!
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(true)
        
        titleLbl.text = pageTitle
        imgView.image = UIImage(named: pageImg)
    }
}
