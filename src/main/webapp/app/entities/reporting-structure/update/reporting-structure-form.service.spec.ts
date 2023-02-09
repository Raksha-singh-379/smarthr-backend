import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../reporting-structure.test-samples';

import { ReportingStructureFormService } from './reporting-structure-form.service';

describe('ReportingStructure Form Service', () => {
  let service: ReportingStructureFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReportingStructureFormService);
  });

  describe('Service methods', () => {
    describe('createReportingStructureFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createReportingStructureFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            employeeId: expect.any(Object),
            reportingEmpId: expect.any(Object),
            status: expect.any(Object),
            reportingStrId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });

      it('passing IReportingStructure should create a new form with FormGroup', () => {
        const formGroup = service.createReportingStructureFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            employeeId: expect.any(Object),
            reportingEmpId: expect.any(Object),
            status: expect.any(Object),
            reportingStrId: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });
    });

    describe('getReportingStructure', () => {
      it('should return NewReportingStructure for default ReportingStructure initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createReportingStructureFormGroup(sampleWithNewData);

        const reportingStructure = service.getReportingStructure(formGroup) as any;

        expect(reportingStructure).toMatchObject(sampleWithNewData);
      });

      it('should return NewReportingStructure for empty ReportingStructure initial value', () => {
        const formGroup = service.createReportingStructureFormGroup();

        const reportingStructure = service.getReportingStructure(formGroup) as any;

        expect(reportingStructure).toMatchObject({});
      });

      it('should return IReportingStructure', () => {
        const formGroup = service.createReportingStructureFormGroup(sampleWithRequiredData);

        const reportingStructure = service.getReportingStructure(formGroup) as any;

        expect(reportingStructure).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IReportingStructure should not enable id FormControl', () => {
        const formGroup = service.createReportingStructureFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewReportingStructure should disable id FormControl', () => {
        const formGroup = service.createReportingStructureFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
