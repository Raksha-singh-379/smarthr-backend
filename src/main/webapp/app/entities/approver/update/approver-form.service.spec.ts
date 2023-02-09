import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../approver.test-samples';

import { ApproverFormService } from './approver-form.service';

describe('Approver Form Service', () => {
  let service: ApproverFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApproverFormService);
  });

  describe('Service methods', () => {
    describe('createApproverFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createApproverFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            approverName: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            status: expect.any(Object),
            approvalSettingId: expect.any(Object),
            departmentId: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });

      it('passing IApprover should create a new form with FormGroup', () => {
        const formGroup = service.createApproverFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            approverName: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            status: expect.any(Object),
            approvalSettingId: expect.any(Object),
            departmentId: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });
    });

    describe('getApprover', () => {
      it('should return NewApprover for default Approver initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createApproverFormGroup(sampleWithNewData);

        const approver = service.getApprover(formGroup) as any;

        expect(approver).toMatchObject(sampleWithNewData);
      });

      it('should return NewApprover for empty Approver initial value', () => {
        const formGroup = service.createApproverFormGroup();

        const approver = service.getApprover(formGroup) as any;

        expect(approver).toMatchObject({});
      });

      it('should return IApprover', () => {
        const formGroup = service.createApproverFormGroup(sampleWithRequiredData);

        const approver = service.getApprover(formGroup) as any;

        expect(approver).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IApprover should not enable id FormControl', () => {
        const formGroup = service.createApproverFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewApprover should disable id FormControl', () => {
        const formGroup = service.createApproverFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
