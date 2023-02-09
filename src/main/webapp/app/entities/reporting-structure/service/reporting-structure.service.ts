import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReportingStructure, NewReportingStructure } from '../reporting-structure.model';

export type PartialUpdateReportingStructure = Partial<IReportingStructure> & Pick<IReportingStructure, 'id'>;

type RestOf<T extends IReportingStructure | NewReportingStructure> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestReportingStructure = RestOf<IReportingStructure>;

export type NewRestReportingStructure = RestOf<NewReportingStructure>;

export type PartialUpdateRestReportingStructure = RestOf<PartialUpdateReportingStructure>;

export type EntityResponseType = HttpResponse<IReportingStructure>;
export type EntityArrayResponseType = HttpResponse<IReportingStructure[]>;

@Injectable({ providedIn: 'root' })
export class ReportingStructureService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/reporting-structures');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reportingStructure: NewReportingStructure): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportingStructure);
    return this.http
      .post<RestReportingStructure>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(reportingStructure: IReportingStructure): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportingStructure);
    return this.http
      .put<RestReportingStructure>(`${this.resourceUrl}/${this.getReportingStructureIdentifier(reportingStructure)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(reportingStructure: PartialUpdateReportingStructure): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reportingStructure);
    return this.http
      .patch<RestReportingStructure>(`${this.resourceUrl}/${this.getReportingStructureIdentifier(reportingStructure)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestReportingStructure>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestReportingStructure[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReportingStructureIdentifier(reportingStructure: Pick<IReportingStructure, 'id'>): number {
    return reportingStructure.id;
  }

  compareReportingStructure(o1: Pick<IReportingStructure, 'id'> | null, o2: Pick<IReportingStructure, 'id'> | null): boolean {
    return o1 && o2 ? this.getReportingStructureIdentifier(o1) === this.getReportingStructureIdentifier(o2) : o1 === o2;
  }

  addReportingStructureToCollectionIfMissing<Type extends Pick<IReportingStructure, 'id'>>(
    reportingStructureCollection: Type[],
    ...reportingStructuresToCheck: (Type | null | undefined)[]
  ): Type[] {
    const reportingStructures: Type[] = reportingStructuresToCheck.filter(isPresent);
    if (reportingStructures.length > 0) {
      const reportingStructureCollectionIdentifiers = reportingStructureCollection.map(
        reportingStructureItem => this.getReportingStructureIdentifier(reportingStructureItem)!
      );
      const reportingStructuresToAdd = reportingStructures.filter(reportingStructureItem => {
        const reportingStructureIdentifier = this.getReportingStructureIdentifier(reportingStructureItem);
        if (reportingStructureCollectionIdentifiers.includes(reportingStructureIdentifier)) {
          return false;
        }
        reportingStructureCollectionIdentifiers.push(reportingStructureIdentifier);
        return true;
      });
      return [...reportingStructuresToAdd, ...reportingStructureCollection];
    }
    return reportingStructureCollection;
  }

  protected convertDateFromClient<T extends IReportingStructure | NewReportingStructure | PartialUpdateReportingStructure>(
    reportingStructure: T
  ): RestOf<T> {
    return {
      ...reportingStructure,
      lastModified: reportingStructure.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restReportingStructure: RestReportingStructure): IReportingStructure {
    return {
      ...restReportingStructure,
      lastModified: restReportingStructure.lastModified ? dayjs(restReportingStructure.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestReportingStructure>): HttpResponse<IReportingStructure> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestReportingStructure[]>): HttpResponse<IReportingStructure[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
