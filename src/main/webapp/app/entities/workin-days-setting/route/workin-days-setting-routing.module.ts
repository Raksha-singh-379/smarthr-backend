import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { WorkinDaysSettingComponent } from '../list/workin-days-setting.component';
import { WorkinDaysSettingDetailComponent } from '../detail/workin-days-setting-detail.component';
import { WorkinDaysSettingUpdateComponent } from '../update/workin-days-setting-update.component';
import { WorkinDaysSettingRoutingResolveService } from './workin-days-setting-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const workinDaysSettingRoute: Routes = [
  {
    path: '',
    component: WorkinDaysSettingComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WorkinDaysSettingDetailComponent,
    resolve: {
      workinDaysSetting: WorkinDaysSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WorkinDaysSettingUpdateComponent,
    resolve: {
      workinDaysSetting: WorkinDaysSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WorkinDaysSettingUpdateComponent,
    resolve: {
      workinDaysSetting: WorkinDaysSettingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(workinDaysSettingRoute)],
  exports: [RouterModule],
})
export class WorkinDaysSettingRoutingModule {}
