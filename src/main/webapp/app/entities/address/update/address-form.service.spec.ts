import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../address.test-samples';

import { AddressFormService } from './address-form.service';

describe('Address Form Service', () => {
  let service: AddressFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddressFormService);
  });

  describe('Service methods', () => {
    describe('createAddressFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAddressFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            line1: expect.any(Object),
            line2: expect.any(Object),
            country: expect.any(Object),
            state: expect.any(Object),
            city: expect.any(Object),
            pincode: expect.any(Object),
            defaultAdd: expect.any(Object),
            landMark: expect.any(Object),
            longitude: expect.any(Object),
            latitude: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            status: expect.any(Object),
            refTableType: expect.any(Object),
            refTableId: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });

      it('passing IAddress should create a new form with FormGroup', () => {
        const formGroup = service.createAddressFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            type: expect.any(Object),
            line1: expect.any(Object),
            line2: expect.any(Object),
            country: expect.any(Object),
            state: expect.any(Object),
            city: expect.any(Object),
            pincode: expect.any(Object),
            defaultAdd: expect.any(Object),
            landMark: expect.any(Object),
            longitude: expect.any(Object),
            latitude: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            status: expect.any(Object),
            refTableType: expect.any(Object),
            refTableId: expect.any(Object),
            companyId: expect.any(Object),
          })
        );
      });
    });

    describe('getAddress', () => {
      it('should return NewAddress for default Address initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAddressFormGroup(sampleWithNewData);

        const address = service.getAddress(formGroup) as any;

        expect(address).toMatchObject(sampleWithNewData);
      });

      it('should return NewAddress for empty Address initial value', () => {
        const formGroup = service.createAddressFormGroup();

        const address = service.getAddress(formGroup) as any;

        expect(address).toMatchObject({});
      });

      it('should return IAddress', () => {
        const formGroup = service.createAddressFormGroup(sampleWithRequiredData);

        const address = service.getAddress(formGroup) as any;

        expect(address).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAddress should not enable id FormControl', () => {
        const formGroup = service.createAddressFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAddress should disable id FormControl', () => {
        const formGroup = service.createAddressFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
