import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IApprover } from '../approver.model';
import { ApproverService } from '../service/approver.service';

@Injectable({ providedIn: 'root' })
export class ApproverRoutingResolveService implements Resolve<IApprover | null> {
  constructor(protected service: ApproverService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApprover | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((approver: HttpResponse<IApprover>) => {
          if (approver.body) {
            return of(approver.body);
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
