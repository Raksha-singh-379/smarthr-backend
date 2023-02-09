import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReportingStructureComponent } from './list/reporting-structure.component';
import { ReportingStructureDetailComponent } from './detail/reporting-structure-detail.component';
import { ReportingStructureUpdateComponent } from './update/reporting-structure-update.component';
import { ReportingStructureDeleteDialogComponent } from './delete/reporting-structure-delete-dialog.component';
import { ReportingStructureRoutingModule } from './route/reporting-structure-routing.module';

@NgModule({
  imports: [SharedModule, ReportingStructureRoutingModule],
  declarations: [
    ReportingStructureComponent,
    ReportingStructureDetailComponent,
    ReportingStructureUpdateComponent,
    ReportingStructureDeleteDialogComponent,
  ],
})
export class ReportingStructureModule {}
