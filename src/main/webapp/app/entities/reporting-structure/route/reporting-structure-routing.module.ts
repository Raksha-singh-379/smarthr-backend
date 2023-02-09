import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ReportingStructureComponent } from '../list/reporting-structure.component';
import { ReportingStructureDetailComponent } from '../detail/reporting-structure-detail.component';
import { ReportingStructureUpdateComponent } from '../update/reporting-structure-update.component';
import { ReportingStructureRoutingResolveService } from './reporting-structure-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const reportingStructureRoute: Routes = [
  {
    path: '',
    component: ReportingStructureComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ReportingStructureDetailComponent,
    resolve: {
      reportingStructure: ReportingStructureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ReportingStructureUpdateComponent,
    resolve: {
      reportingStructure: ReportingStructureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ReportingStructureUpdateComponent,
    resolve: {
      reportingStructure: ReportingStructureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(reportingStructureRoute)],
  exports: [RouterModule],
})
export class ReportingStructureRoutingModule {}
