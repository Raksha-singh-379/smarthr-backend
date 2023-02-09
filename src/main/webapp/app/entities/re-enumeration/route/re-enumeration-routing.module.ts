import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReEnumerationComponent } from '../list/re-enumeration.component';
import { ReEnumerationDetailComponent } from '../detail/re-enumeration-detail.component';
import { ReEnumerationUpdateComponent } from '../update/re-enumeration-update.component';
import { ReEnumerationRoutingResolveService } from './re-enumeration-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const reEnumerationRoute: Routes = [
  {
    path: '',
    component: ReEnumerationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReEnumerationDetailComponent,
    resolve: {
      reEnumeration: ReEnumerationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReEnumerationUpdateComponent,
    resolve: {
      reEnumeration: ReEnumerationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReEnumerationUpdateComponent,
    resolve: {
      reEnumeration: ReEnumerationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reEnumerationRoute)],
  exports: [RouterModule],
})
export class ReEnumerationRoutingModule {}
