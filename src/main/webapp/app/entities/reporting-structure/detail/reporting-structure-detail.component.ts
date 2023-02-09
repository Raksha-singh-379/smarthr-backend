import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportingStructure } from '../reporting-structure.model';

@Component({
  selector: 'jhi-reporting-structure-detail',
  templateUrl: './reporting-structure-detail.component.html',
})
export class ReportingStructureDetailComponent implements OnInit {
  reportingStructure: IReportingStructure | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reportingStructure }) => {
      this.reportingStructure = reportingStructure;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
