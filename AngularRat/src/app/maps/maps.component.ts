import { Component, OnInit, ViewEncapsulation, ViewChild, ElementRef } from '@angular/core';
import { RatdataService } from '../ratdata.service';

declare var google;

@Component({
  selector: 'app-maps',
  templateUrl: './maps.component.html',
  styleUrls: ['./maps.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MapsComponent implements OnInit {
  @ViewChild('map') mapElement: ElementRef;

  private beginDate: string;
  private endDate: string;

  private map: any;
  private markers: any[] = [];

  constructor(private rs: RatdataService) { }

  ngOnInit() {
    this.initMap();
  }

  initMap() {
    var uluru = {lat: 40.7128, lng: -74.0060};
    this.map = new google.maps.Map(this.mapElement.nativeElement, {
      center: uluru,
      zoom: 15
    });
  }

  getMapData() {
    this.rs.getRatDataByDateRange(new Date(this.beginDate), new Date(this.endDate)).subscribe((data) => {
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
      for (let rd of data) {
        var marker = new google.maps.Marker({
          position: {lat: rd.latitude, lng: rd.longitude},
          map: this.map
        });
        this.markers.push(marker);
      }
    })
  }
}
