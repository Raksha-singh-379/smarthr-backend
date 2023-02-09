import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../workin-days-setting.test-samples';

import { WorkinDaysSettingFormService } from './workin-days-setting-form.service';

describe('WorkinDaysSetting Form Service', () => {
  let service: WorkinDaysSettingFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkinDaysSettingFormService);
  });

  describe('Service methods', () => {
    describe('createWorkinDaysSettingFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWorkinDaysSettingFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            status: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });

      it('passing IWorkinDaysSetting should create a new form with FormGroup', () => {
        const formGroup = service.createWorkinDaysSettingFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            status: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });
    });

    describe('getWorkinDaysSetting', () => {
      it('should return NewWorkinDaysSetting for default WorkinDaysSetting initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createWorkinDaysSettingFormGroup(sampleWithNewData);

        const workinDaysSetting = service.getWorkinDaysSetting(formGroup) as any;

        expect(workinDaysSetting).toMatchObject(sampleWithNewData);
      });

      it('should return NewWorkinDaysSetting for empty WorkinDaysSetting initial value', () => {
        const formGroup = service.createWorkinDaysSettingFormGroup();

        const workinDaysSetting = service.getWorkinDaysSetting(formGroup) as any;

        expect(workinDaysSetting).toMatchObject({});
      });

      it('should return IWorkinDaysSetting', () => {
        const formGroup = service.createWorkinDaysSettingFormGroup(sampleWithRequiredData);

        const workinDaysSetting = service.getWorkinDaysSetting(formGroup) as any;

        expect(workinDaysSetting).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWorkinDaysSetting should not enable id FormControl', () => {
        const formGroup = service.createWorkinDaysSettingFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWorkinDaysSetting should disable id FormControl', () => {
        const formGroup = service.createWorkinDaysSettingFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
