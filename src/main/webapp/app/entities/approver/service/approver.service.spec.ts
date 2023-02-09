import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IApprover } from '../approver.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../approver.test-samples';

import { ApproverService, RestApprover } from './approver.service';

const requireRestSample: RestApprover = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('Approver Service', () => {
  let service: ApproverService;
  let httpMock: HttpTestingController;
  let expectedResult: IApprover | IApprover[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ApproverService);
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

    it('should create a Approver', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const approver = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(approver).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Approver', () => {
      const approver = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(approver).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Approver', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Approver', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Approver', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addApproverToCollectionIfMissing', () => {
      it('should add a Approver to an empty array', () => {
        const approver: IApprover = sampleWithRequiredData;
        expectedResult = service.addApproverToCollectionIfMissing([], approver);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(approver);
      });

      it('should not add a Approver to an array that contains it', () => {
        const approver: IApprover = sampleWithRequiredData;
        const approverCollection: IApprover[] = [
          {
            ...approver,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addApproverToCollectionIfMissing(approverCollection, approver);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Approver to an array that doesn't contain it", () => {
        const approver: IApprover = sampleWithRequiredData;
        const approverCollection: IApprover[] = [sampleWithPartialData];
        expectedResult = service.addApproverToCollectionIfMissing(approverCollection, approver);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(approver);
      });

      it('should add only unique Approver to an array', () => {
        const approverArray: IApprover[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const approverCollection: IApprover[] = [sampleWithRequiredData];
        expectedResult = service.addApproverToCollectionIfMissing(approverCollection, ...approverArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const approver: IApprover = sampleWithRequiredData;
        const approver2: IApprover = sampleWithPartialData;
        expectedResult = service.addApproverToCollectionIfMissing([], approver, approver2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(approver);
        expect(expectedResult).toContain(approver2);
      });

      it('should accept null and undefined values', () => {
        const approver: IApprover = sampleWithRequiredData;
        expectedResult = service.addApproverToCollectionIfMissing([], null, approver, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(approver);
      });

      it('should return initial array if no Approver is added', () => {
        const approverCollection: IApprover[] = [sampleWithRequiredData];
        expectedResult = service.addApproverToCollectionIfMissing(approverCollection, undefined, null);
        expect(expectedResult).toEqual(approverCollection);
      });
    });

    describe('compareApprover', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareApprover(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareApprover(entity1, entity2);
        const compareResult2 = service.compareApprover(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareApprover(entity1, entity2);
        const compareResult2 = service.compareApprover(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareApprover(entity1, entity2);
        const compareResult2 = service.compareApprover(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
