import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReEnumeration } from '../re-enumeration.model';

@Component({
  selector: 'jhi-re-enumeration-detail',
  templateUrl: './re-enumeration-detail.component.html',
})
export class ReEnumerationDetailComponent implements OnInit {
  reEnumeration: IReEnumeration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reEnumeration }) => {
      this.reEnumeration = reEnumeration;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
