import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IWorkinDaysSetting } from '../workin-days-setting.model';
import { WorkinDaysSettingService } from '../service/workin-days-setting.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './workin-days-setting-delete-dialog.component.html',
})
export class WorkinDaysSettingDeleteDialogComponent {
  workinDaysSetting?: IWorkinDaysSetting;

  constructor(protected workinDaysSettingService: WorkinDaysSettingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workinDaysSettingService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
