import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IReEnumeration } from '../re-enumeration.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../re-enumeration.test-samples';

import { ReEnumerationService, RestReEnumeration } from './re-enumeration.service';

const requireRestSample: RestReEnumeration = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('ReEnumeration Service', () => {
  let service: ReEnumerationService;
  let httpMock: HttpTestingController;
  let expectedResult: IReEnumeration | IReEnumeration[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReEnumerationService);
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

    it('should create a ReEnumeration', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const reEnumeration = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(reEnumeration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReEnumeration', () => {
      const reEnumeration = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(reEnumeration).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReEnumeration', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReEnumeration', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ReEnumeration', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addReEnumerationToCollectionIfMissing', () => {
      it('should add a ReEnumeration to an empty array', () => {
        const reEnumeration: IReEnumeration = sampleWithRequiredData;
        expectedResult = service.addReEnumerationToCollectionIfMissing([], reEnumeration);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reEnumeration);
      });

      it('should not add a ReEnumeration to an array that contains it', () => {
        const reEnumeration: IReEnumeration = sampleWithRequiredData;
        const reEnumerationCollection: IReEnumeration[] = [
          {
            ...reEnumeration,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addReEnumerationToCollectionIfMissing(reEnumerationCollection, reEnumeration);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReEnumeration to an array that doesn't contain it", () => {
        const reEnumeration: IReEnumeration = sampleWithRequiredData;
        const reEnumerationCollection: IReEnumeration[] = [sampleWithPartialData];
        expectedResult = service.addReEnumerationToCollectionIfMissing(reEnumerationCollection, reEnumeration);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reEnumeration);
      });

      it('should add only unique ReEnumeration to an array', () => {
        const reEnumerationArray: IReEnumeration[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const reEnumerationCollection: IReEnumeration[] = [sampleWithRequiredData];
        expectedResult = service.addReEnumerationToCollectionIfMissing(reEnumerationCollection, ...reEnumerationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const reEnumeration: IReEnumeration = sampleWithRequiredData;
        const reEnumeration2: IReEnumeration = sampleWithPartialData;
        expectedResult = service.addReEnumerationToCollectionIfMissing([], reEnumeration, reEnumeration2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reEnumeration);
        expect(expectedResult).toContain(reEnumeration2);
      });

      it('should accept null and undefined values', () => {
        const reEnumeration: IReEnumeration = sampleWithRequiredData;
        expectedResult = service.addReEnumerationToCollectionIfMissing([], null, reEnumeration, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reEnumeration);
      });

      it('should return initial array if no ReEnumeration is added', () => {
        const reEnumerationCollection: IReEnumeration[] = [sampleWithRequiredData];
        expectedResult = service.addReEnumerationToCollectionIfMissing(reEnumerationCollection, undefined, null);
        expect(expectedResult).toEqual(reEnumerationCollection);
      });
    });

    describe('compareReEnumeration', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareReEnumeration(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareReEnumeration(entity1, entity2);
        const compareResult2 = service.compareReEnumeration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareReEnumeration(entity1, entity2);
        const compareResult2 = service.compareReEnumeration(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareReEnumeration(entity1, entity2);
        const compareResult2 = service.compareReEnumeration(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
