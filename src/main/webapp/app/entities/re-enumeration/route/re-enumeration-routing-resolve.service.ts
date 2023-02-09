import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IReEnumeration } from '../re-enumeration.model';
import { ReEnumerationService } from '../service/re-enumeration.service';

@Injectable({ providedIn: 'root' })
export class ReEnumerationRoutingResolveService implements Resolve<IReEnumeration | null> {
  constructor(protected service: ReEnumerationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IReEnumeration | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((reEnumeration: HttpResponse<IReEnumeration>) => {
          if (reEnumeration.body) {
            return of(reEnumeration.body);
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
