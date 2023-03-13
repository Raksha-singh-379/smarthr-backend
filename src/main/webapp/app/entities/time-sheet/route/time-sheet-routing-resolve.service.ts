import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITimeSheet } from '../time-sheet.model';
import { TimeSheetService } from '../service/time-sheet.service';

@Injectable({ providedIn: 'root' })
export class TimeSheetRoutingResolveService implements Resolve<ITimeSheet | null> {
  constructor(protected service: TimeSheetService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimeSheet | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((timeSheet: HttpResponse<ITimeSheet>) => {
          if (timeSheet.body) {
            return of(timeSheet.body);
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
