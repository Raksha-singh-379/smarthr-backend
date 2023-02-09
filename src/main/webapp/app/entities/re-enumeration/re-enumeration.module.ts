import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ReEnumerationComponent } from './list/re-enumeration.component';
import { ReEnumerationDetailComponent } from './detail/re-enumeration-detail.component';
import { ReEnumerationUpdateComponent } from './update/re-enumeration-update.component';
import { ReEnumerationDeleteDialogComponent } from './delete/re-enumeration-delete-dialog.component';
import { ReEnumerationRoutingModule } from './route/re-enumeration-routing.module';

@NgModule({
  imports: [SharedModule, ReEnumerationRoutingModule],
  declarations: [ReEnumerationComponent, ReEnumerationDetailComponent, ReEnumerationUpdateComponent, ReEnumerationDeleteDialogComponent],
})
export class ReEnumerationModule {}
