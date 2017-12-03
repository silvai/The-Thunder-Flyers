import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RatData } from '../ratdata';
import { LocationType } from '../locationtype';
import { Borough } from '../borough';
import { RatdataService } from '../ratdata.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap/modal/modal-ref';

@Component({
  selector: 'ngbd-modal-content',
  templateUrl: "./ratdataentry.component.html"
})
export class NgbdModalContent {
  private address: string;
  private city: string;
  private zip: number;

  private boroughs: string[] = ["Manhattan", "The Bronx", "Staten Island", "Brooklyn", "Queens"];
  private borough: string = "Manhattan";

  private locationtypes: string[] = ["3+ Family Mixed Use Building", "Commercial Building", "1-2 Family Dwelling", "3+ Family Apt. Building",
    "Public Stairs", "Other (Explain Below)", "Hospital", "Construction Site", "Vacant Lot", "Vacant Building", "Parking Lot/Garage",
    "Public Garden", "1-2 Family Mixed Use Building", "Catch Basin/Sewer", "Day Care/Nursery", "Government Building", "Office Building",
    "School/Pre-School","Single Room Occupancy (SRO)"];
  private locationType: string = "3+ Family Mixed Use Building";

  constructor(public activeModal: NgbActiveModal, private rs: RatdataService) {}

  onSubmit() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        const id: number = parseInt(localStorage.getItem("userid"));
        const ratreport = new RatData(this.locationType, this.zip, this.address, this.city, this.borough, latitude, longitude, id);
        this.rs.addRatData(ratreport).subscribe((message) => {
          if (message.success) {
            this.activeModal.close();
          }
        });
      });
    } else {
      alert("Cannot add rat report unless geolocation is enabled.");
    }
    
  }
}

@Component({
  selector: 'app-ratdataentry',
  template: '<button class="btn btn-lg btn-outline-primary" (click)="open()">Submit Rat Report</button><br/><br/>',
  styleUrls: ['./ratdataentry.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RatdataentryComponent implements OnInit {
  

  private ref: NgbModalRef;

  constructor(private modalService: NgbModal) { }

  ngOnInit() {
  }

  open() {
    this.ref = this.modalService.open(NgbdModalContent);
  }
}
