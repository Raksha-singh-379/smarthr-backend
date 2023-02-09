import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IState } from '../state.model';
import { StateService } from '../service/state.service';

@Injectable({ providedIn: 'root' })
export class StateRoutingResolveService implements Resolve<IState | null> {
  constructor(protected service: StateService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IState | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((state: HttpResponse<IState>) => {
          if (state.body) {
            return of(state.body);
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
