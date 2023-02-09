import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../re-enumeration.test-samples';

import { ReEnumerationFormService } from './re-enumeration-form.service';

describe('ReEnumeration Form Service', () => {
  let service: ReEnumerationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReEnumerationFormService);
  });

  describe('Service methods', () => {
    describe('createReEnumerationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReEnumerationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            salaryBasis: expect.any(Object),
            amount: expect.any(Object),
            paymentType: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            status: expect.any(Object),
            employeId: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });

      it('passing IReEnumeration should create a new form with FormGroup', () => {
        const formGroup = service.createReEnumerationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            salaryBasis: expect.any(Object),
            amount: expect.any(Object),
            paymentType: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            status: expect.any(Object),
            employeId: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });
    });

    describe('getReEnumeration', () => {
      it('should return NewReEnumeration for default ReEnumeration initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createReEnumerationFormGroup(sampleWithNewData);

        const reEnumeration = service.getReEnumeration(formGroup) as any;

        expect(reEnumeration).toMatchObject(sampleWithNewData);
      });

      it('should return NewReEnumeration for empty ReEnumeration initial value', () => {
        const formGroup = service.createReEnumerationFormGroup();

        const reEnumeration = service.getReEnumeration(formGroup) as any;

        expect(reEnumeration).toMatchObject({});
      });

      it('should return IReEnumeration', () => {
        const formGroup = service.createReEnumerationFormGroup(sampleWithRequiredData);

        const reEnumeration = service.getReEnumeration(formGroup) as any;

        expect(reEnumeration).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReEnumeration should not enable id FormControl', () => {
        const formGroup = service.createReEnumerationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReEnumeration should disable id FormControl', () => {
        const formGroup = service.createReEnumerationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
