import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IReEnumeration } from '../re-enumeration.model';
import { ReEnumerationService } from '../service/re-enumeration.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './re-enumeration-delete-dialog.component.html',
})
export class ReEnumerationDeleteDialogComponent {
  reEnumeration?: IReEnumeration;

  constructor(protected reEnumerationService: ReEnumerationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reEnumerationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
