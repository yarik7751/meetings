//
//  WomanSearchViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 10.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanSearchViewController: UIPageViewController {

  var mapVC: WomanSearchMapViewController!
  var listVC: WomanSearchListViewController!
  var isViewControllersSetup = false

  override func  viewDidLoad() {
    super.viewDidLoad()
    mapVC = UIStoryboard.viewController(with: "@WomanSearchMap") as! WomanSearchMapViewController
    listVC = UIStoryboard.viewController(with: "@WomanSearchList") as! WomanSearchListViewController

  }

  override func viewDidLayoutSubviews() {
    super.viewDidLayoutSubviews()
    guard !isViewControllersSetup else {return}
    setViewControllers([mapVC], direction: .forward, animated: true, completion: nil)
    isViewControllersSetup = true
  }

  @IBAction func switchView(_ sender: UISegmentedControl) {
    if sender.selectedSegmentIndex == 0 {
      setViewControllers([mapVC], direction: .reverse, animated: true, completion: nil)
    } else {
      setViewControllers([listVC], direction: .forward, animated: true, completion: nil)
    }
  }

}
