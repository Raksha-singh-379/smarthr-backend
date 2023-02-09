import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReportingStructure } from '../reporting-structure.model';
import { ReportingStructureService } from '../service/reporting-structure.service';

@Injectable({ providedIn: 'root' })
export class ReportingStructureRoutingResolveService implements Resolve<IReportingStructure | null> {
  constructor(protected service: ReportingStructureService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReportingStructure | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((reportingStructure: HttpResponse<IReportingStructure>) => {
          if (reportingStructure.body) {
            return of(reportingStructure.body);
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
