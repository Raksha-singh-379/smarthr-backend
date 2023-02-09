import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkinDaysSetting } from '../workin-days-setting.model';

@Component({
  selector: 'jhi-workin-days-setting-detail',
  templateUrl: './workin-days-setting-detail.component.html',
})
export class WorkinDaysSettingDetailComponent implements OnInit {
  workinDaysSetting: IWorkinDaysSetting | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workinDaysSetting }) => {
      this.workinDaysSetting = workinDaysSetting;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
