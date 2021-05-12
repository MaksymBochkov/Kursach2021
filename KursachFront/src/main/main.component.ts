import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Advertisement} from '../entity/advertisement';
import {newArray} from "@angular/compiler/src/util";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  baseUrl = 'http://localhost:8080/Kursach2021';
  public advertisements: any;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.getAllAdvertisment();
  }

  public getAllAdvertisment() {
    this.http.get<any>(this.baseUrl + '/advertisement?actionName=getAllAdvertisement')
      .subscribe((adv: any) => {
        console.log('advertismentList:');
        console.log(adv);
        this.advertisements = adv;
        console.log(this.advertisements);
      });
  }

}
