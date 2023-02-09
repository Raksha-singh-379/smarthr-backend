import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IReportingStructure } from '../reporting-structure.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../reporting-structure.test-samples';

import { ReportingStructureService, RestReportingStructure } from './reporting-structure.service';

const requireRestSample: RestReportingStructure = {
  ...sampleWithRequiredData,
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('ReportingStructure Service', () => {
  let service: ReportingStructureService;
  let httpMock: HttpTestingController;
  let expectedResult: IReportingStructure | IReportingStructure[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ReportingStructureService);
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

    it('should create a ReportingStructure', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const reportingStructure = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(reportingStructure).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ReportingStructure', () => {
      const reportingStructure = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(reportingStructure).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ReportingStructure', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ReportingStructure', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ReportingStructure', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addReportingStructureToCollectionIfMissing', () => {
      it('should add a ReportingStructure to an empty array', () => {
        const reportingStructure: IReportingStructure = sampleWithRequiredData;
        expectedResult = service.addReportingStructureToCollectionIfMissing([], reportingStructure);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reportingStructure);
      });

      it('should not add a ReportingStructure to an array that contains it', () => {
        const reportingStructure: IReportingStructure = sampleWithRequiredData;
        const reportingStructureCollection: IReportingStructure[] = [
          {
            ...reportingStructure,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addReportingStructureToCollectionIfMissing(reportingStructureCollection, reportingStructure);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ReportingStructure to an array that doesn't contain it", () => {
        const reportingStructure: IReportingStructure = sampleWithRequiredData;
        const reportingStructureCollection: IReportingStructure[] = [sampleWithPartialData];
        expectedResult = service.addReportingStructureToCollectionIfMissing(reportingStructureCollection, reportingStructure);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reportingStructure);
      });

      it('should add only unique ReportingStructure to an array', () => {
        const reportingStructureArray: IReportingStructure[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const reportingStructureCollection: IReportingStructure[] = [sampleWithRequiredData];
        expectedResult = service.addReportingStructureToCollectionIfMissing(reportingStructureCollection, ...reportingStructureArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const reportingStructure: IReportingStructure = sampleWithRequiredData;
        const reportingStructure2: IReportingStructure = sampleWithPartialData;
        expectedResult = service.addReportingStructureToCollectionIfMissing([], reportingStructure, reportingStructure2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(reportingStructure);
        expect(expectedResult).toContain(reportingStructure2);
      });

      it('should accept null and undefined values', () => {
        const reportingStructure: IReportingStructure = sampleWithRequiredData;
        expectedResult = service.addReportingStructureToCollectionIfMissing([], null, reportingStructure, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(reportingStructure);
      });

      it('should return initial array if no ReportingStructure is added', () => {
        const reportingStructureCollection: IReportingStructure[] = [sampleWithRequiredData];
        expectedResult = service.addReportingStructureToCollectionIfMissing(reportingStructureCollection, undefined, null);
        expect(expectedResult).toEqual(reportingStructureCollection);
      });
    });

    describe('compareReportingStructure', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareReportingStructure(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareReportingStructure(entity1, entity2);
        const compareResult2 = service.compareReportingStructure(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareReportingStructure(entity1, entity2);
        const compareResult2 = service.compareReportingStructure(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareReportingStructure(entity1, entity2);
        const compareResult2 = service.compareReportingStructure(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
