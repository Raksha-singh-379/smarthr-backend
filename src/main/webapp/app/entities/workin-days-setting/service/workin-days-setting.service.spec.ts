import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWorkinDaysSetting } from '../workin-days-setting.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../workin-days-setting.test-samples';

import { WorkinDaysSettingService, RestWorkinDaysSetting } from './workin-days-setting.service';

const requireRestSample: RestWorkinDaysSetting = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('WorkinDaysSetting Service', () => {
  let service: WorkinDaysSettingService;
  let httpMock: HttpTestingController;
  let expectedResult: IWorkinDaysSetting | IWorkinDaysSetting[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WorkinDaysSettingService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a WorkinDaysSetting', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const workinDaysSetting = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(workinDaysSetting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WorkinDaysSetting', () => {
      const workinDaysSetting = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(workinDaysSetting).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a WorkinDaysSetting', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WorkinDaysSetting', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a WorkinDaysSetting', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWorkinDaysSettingToCollectionIfMissing', () => {
      it('should add a WorkinDaysSetting to an empty array', () => {
        const workinDaysSetting: IWorkinDaysSetting = sampleWithRequiredData;
        expectedResult = service.addWorkinDaysSettingToCollectionIfMissing([], workinDaysSetting);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workinDaysSetting);
      });

      it('should not add a WorkinDaysSetting to an array that contains it', () => {
        const workinDaysSetting: IWorkinDaysSetting = sampleWithRequiredData;
        const workinDaysSettingCollection: IWorkinDaysSetting[] = [
          {
            ...workinDaysSetting,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWorkinDaysSettingToCollectionIfMissing(workinDaysSettingCollection, workinDaysSetting);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WorkinDaysSetting to an array that doesn't contain it", () => {
        const workinDaysSetting: IWorkinDaysSetting = sampleWithRequiredData;
        const workinDaysSettingCollection: IWorkinDaysSetting[] = [sampleWithPartialData];
        expectedResult = service.addWorkinDaysSettingToCollectionIfMissing(workinDaysSettingCollection, workinDaysSetting);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workinDaysSetting);
      });

      it('should add only unique WorkinDaysSetting to an array', () => {
        const workinDaysSettingArray: IWorkinDaysSetting[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const workinDaysSettingCollection: IWorkinDaysSetting[] = [sampleWithRequiredData];
        expectedResult = service.addWorkinDaysSettingToCollectionIfMissing(workinDaysSettingCollection, ...workinDaysSettingArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const workinDaysSetting: IWorkinDaysSetting = sampleWithRequiredData;
        const workinDaysSetting2: IWorkinDaysSetting = sampleWithPartialData;
        expectedResult = service.addWorkinDaysSettingToCollectionIfMissing([], workinDaysSetting, workinDaysSetting2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workinDaysSetting);
        expect(expectedResult).toContain(workinDaysSetting2);
      });

      it('should accept null and undefined values', () => {
        const workinDaysSetting: IWorkinDaysSetting = sampleWithRequiredData;
        expectedResult = service.addWorkinDaysSettingToCollectionIfMissing([], null, workinDaysSetting, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workinDaysSetting);
      });

      it('should return initial array if no WorkinDaysSetting is added', () => {
        const workinDaysSettingCollection: IWorkinDaysSetting[] = [sampleWithRequiredData];
        expectedResult = service.addWorkinDaysSettingToCollectionIfMissing(workinDaysSettingCollection, undefined, null);
        expect(expectedResult).toEqual(workinDaysSettingCollection);
      });
    });

    describe('compareWorkinDaysSetting', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWorkinDaysSetting(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareWorkinDaysSetting(entity1, entity2);
        const compareResult2 = service.compareWorkinDaysSetting(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareWorkinDaysSetting(entity1, entity2);
        const compareResult2 = service.compareWorkinDaysSetting(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareWorkinDaysSetting(entity1, entity2);
        const compareResult2 = service.compareWorkinDaysSetting(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
