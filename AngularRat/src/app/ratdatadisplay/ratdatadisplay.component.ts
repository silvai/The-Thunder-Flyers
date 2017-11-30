import { Component, OnInit, ViewEncapsulation, Input, Output, EventEmitter } from '@angular/core';
import { RatdataService } from '../ratdata.service';
import { RatData } from '../ratdata';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-ratdatadisplay',
  templateUrl: './ratdatadisplay.component.html',
  styleUrls: ['./ratdatadisplay.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RatdatadisplayComponent implements OnInit {

  currPage: number;
  count: number;
  perPage: number;
  loading: boolean = false;

  totalPages: number;
  displayedData: RatData[];

  openModal: NgbModalRef;

  constructor(private rs: RatdataService, private modalService: NgbModal) { }

  ngOnInit() {
    this.currPage = 1;
    this.getRatData();
  }

  getMinDataIndex(): number {
    return ((this.perPage * this.currPage) - this.perPage) + 1;
  }

  getMaxDataIndex(): number {
    let max = this.perPage * this.currPage;
    if (max > this.count) {
      max = this.count;
    }
    return max;
  }

  lastPage(): boolean {
    return this.currPage >= this.totalPages;
  }

  getPages(): number[] {
    let p = this.currPage || 1;
    const numPagesToShow = 10;
    const pages: number[] = [];
    pages.push(p);
    const times = numPagesToShow - 1;

    for (let i = 0; i < times; i++) {
      if (pages.length < numPagesToShow) {
        if (Math.min.apply(null, pages) > 1) {
          pages.push(Math.min.apply(null, pages) - 1);
        }
      }

      if (pages.length < numPagesToShow) {
        if (pages.length < numPagesToShow) {
          if (Math.max.apply(null, pages) < this.totalPages) {
            pages.push(Math.max.apply(null, pages) + 1);
          }
        }
      }
    }

    pages.sort((a, b) => a - b);
    return pages;
  }

  prev(): void {
    this.currPage--;
    this.getRatData();
  }

  next(): void {
    this.currPage++;
    this.getRatData();
  }

  goToPage(pageNum: number): void {
    this.currPage = pageNum;
    this.getRatData();
  }

  getRatData(): void {
    this.loading = true;
    this.rs.getRatDataPagination().subscribe((data) => {
      this.count = data.count;
      this.perPage = data.perPage;
      this.totalPages = data.numPages;
    });
    this.rs.getRatDataByPage(this.currPage).subscribe((data) => {
      this.loading = false;
      this.displayedData = data;
    });
  }

  open(ratdatamodal) : void {
    this.openModal = this.modalService.open(ratdatamodal);
  }

  dismiss(reason): void {
    this.openModal.close(reason);
  }
}
