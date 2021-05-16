import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {DataGetterService} from "../app/data-getter.service";
import {Router} from "@angular/router";
import {Advertisement} from "../entity/advertisement";

@Component({
  selector: 'app-my-advertisements',
  templateUrl: './my-advertisements.component.html',
  styleUrls: ['./my-advertisements.component.css']
})
export class MyAdvertisementsComponent implements OnInit {
  baseUrl = 'http://localhost:8080/Kursach2021';
  public user: any;
  public currentUserId: number;
  public advertisements: any;

  constructor(private http: HttpClient,
              private dataGetter: DataGetterService,
              private router: Router)
  {
    this.currentUserId = dataGetter.getCurrentUserId();
    dataGetter.getUserById(this.currentUserId).subscribe(user => this.user = user);
    this.getAdvertisementsOfCurrentUser();
  }

  ngOnInit(): void {
  }

  public getAdvertisementsOfCurrentUser() {
    this.http.get<any>(this.baseUrl + '/advertisement?actionName=getAdvertisementsOfCurrentUser')
      .subscribe((advList: any) => {
        this.advertisements = advList;
      });
  }

  public goToAdvertisementView(advertisement: Advertisement){
    this.dataGetter.setUserId(advertisement.userId);
    this.dataGetter.setCurrentUserId(this.currentUserId);
    this.dataGetter.setAdvertisement(advertisement);
    this.dataGetter.goToAdvertisementView();
  }

  goToFavorites() {
    this.dataGetter.setCurrentUserId(this.currentUserId);
    this.dataGetter.goToFavorites();
  }

  goToMyAdvertisements() {
    this.dataGetter.setCurrentUserId(this.user.userId);
    this.dataGetter.goToMyAdvertisements();
  }

  public logOut(){
    this.dataGetter.logOut();
  }
}