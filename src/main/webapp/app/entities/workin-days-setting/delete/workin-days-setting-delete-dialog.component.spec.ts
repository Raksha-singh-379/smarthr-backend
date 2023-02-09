jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { WorkinDaysSettingService } from '../service/workin-days-setting.service';

import { WorkinDaysSettingDeleteDialogComponent } from './workin-days-setting-delete-dialog.component';

describe('WorkinDaysSetting Management Delete Component', () => {
  let comp: WorkinDaysSettingDeleteDialogComponent;
  let fixture: ComponentFixture<WorkinDaysSettingDeleteDialogComponent>;
  let service: WorkinDaysSettingService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [WorkinDaysSettingDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(WorkinDaysSettingDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(WorkinDaysSettingDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(WorkinDaysSettingService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
