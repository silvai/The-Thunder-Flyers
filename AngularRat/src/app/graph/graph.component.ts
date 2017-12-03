import { Component, OnInit, ViewEncapsulation, ViewChild, ElementRef } from '@angular/core';
import { Chart } from 'chart.js';
import { RatdataService } from '../ratdata.service';
import { Graphs } from '../graphs';

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class GraphComponent implements OnInit {
  @ViewChild("chart") chartElement: ElementRef;

  private graphTypes: string[] = ["Bar Chart", "Line Chart", "Pie Chart"];
  private selectedType: string = "Bar Chart";

  private beginDate: string;
  private endDate: string;
  private todayDate: string = new Date().toISOString().slice(0, 10);
  private graphType: Graphs;

  private chartRef: Chart;

  constructor(private rs: RatdataService) { }

  ngOnInit() {
    this.chartRef = new Chart(this.chartElement.nativeElement, null, {
      width: 500,
      height: 500
    });
  }

  getMonthDiff(d1: Date, d2: Date): number {
    return (((d2.getFullYear() - d1.getFullYear()) * 12) + d2.getMonth() - d1.getMonth());
  }

  getChartData() {
    if (this.selectedType === "Bar Chart") {
      this.graphType = Graphs.BAR_CHART;
    } else if (this.selectedType === "Line Chart") {
      this.graphType = Graphs.LINE_CHART;
    } else if (this.selectedType === "Pie Chart") {
      this.graphType = Graphs.PIE_CHART;
    }

    this.rs.getRatDataByDateRange(new Date(this.beginDate), new Date(this.endDate)).subscribe((data) => {
      const months: number = this.getMonthDiff(new Date(this.beginDate), new Date(this.endDate)) + 1;
      let entries: Array<number> = new Array<number>(months);
      for (let j = 0; j < entries.length; j++) {
        entries[j] = 0;
      }
      for (let i = 0; i < data.length; i++) {
        let newDate: Date = data[i].createdDate;
        let index: number = months - 1 - this.getMonthDiff(new Date(newDate), new Date(this.endDate));
        entries[index] = entries[index] + 1;
      }
      
      var iter: Date = new Date(this.beginDate);
      iter.setDate(1);
      let labels: Array<string> = new Array<string>(months);
      for (let k = 0; k < labels.length; k++) {
        labels[k] = iter.toISOString().slice(0, 7);
        iter.setMonth(iter.getMonth() + 1);
      }

      if (this.chartRef) {
        this.chartRef.destroy();
      }

      switch (this.graphType) {
        case Graphs.BAR_CHART:
          this.chartRef = new Chart(this.chartElement.nativeElement, {
            type: 'bar',
            data: {
              labels: labels,
              datasets: [{
                label: "Rat Sightings",
                data: entries
              }]
            },
            options: {
              responsive: false,
              maintainAspectRatio: false
            }
          });
          break;
        case Graphs.LINE_CHART:
          this.chartRef = new Chart(this.chartElement.nativeElement, {
            type: 'line',
            data: {
              labels: labels,
              datasets: [{
                label: "Rat Sightings",
                data: entries
              }]
            },
            options: {
              responsive: false,
              maintainAspectRatio: false
            }
          });
          break;
        case Graphs.PIE_CHART:
          this.chartRef = new Chart(this.chartElement.nativeElement, {
            type: 'pie',
            data: {
              labels: labels,
              datasets: [{
                label: "Rat Sightings",
                data: entries
              }]
            },
            options: {
              responsive: false,
              maintainAspectRatio: false
            }
          });
          break;
      }
    })
  }
}
