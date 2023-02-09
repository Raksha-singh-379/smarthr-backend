import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IWorkinDaysSetting } from '../workin-days-setting.model';
import { WorkinDaysSettingService } from '../service/workin-days-setting.service';

@Injectable({ providedIn: 'root' })
export class WorkinDaysSettingRoutingResolveService implements Resolve<IWorkinDaysSetting | null> {
  constructor(protected service: WorkinDaysSettingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkinDaysSetting | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((workinDaysSetting: HttpResponse<IWorkinDaysSetting>) => {
          if (workinDaysSetting.body) {
            return of(workinDaysSetting.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
