import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { WorkinDaysSettingComponent } from './list/workin-days-setting.component';
import { WorkinDaysSettingDetailComponent } from './detail/workin-days-setting-detail.component';
import { WorkinDaysSettingUpdateComponent } from './update/workin-days-setting-update.component';
import { WorkinDaysSettingDeleteDialogComponent } from './delete/workin-days-setting-delete-dialog.component';
import { WorkinDaysSettingRoutingModule } from './route/workin-days-setting-routing.module';

@NgModule({
  imports: [SharedModule, WorkinDaysSettingRoutingModule],
  declarations: [
    WorkinDaysSettingComponent,
    WorkinDaysSettingDetailComponent,
    WorkinDaysSettingUpdateComponent,
    WorkinDaysSettingDeleteDialogComponent,
  ],
})
export class WorkinDaysSettingModule {}
