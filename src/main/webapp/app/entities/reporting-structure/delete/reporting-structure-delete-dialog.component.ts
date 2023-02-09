import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReportingStructure } from '../reporting-structure.model';
import { ReportingStructureService } from '../service/reporting-structure.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './reporting-structure-delete-dialog.component.html',
})
export class ReportingStructureDeleteDialogComponent {
  reportingStructure?: IReportingStructure;

  constructor(protected reportingStructureService: ReportingStructureService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reportingStructureService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
