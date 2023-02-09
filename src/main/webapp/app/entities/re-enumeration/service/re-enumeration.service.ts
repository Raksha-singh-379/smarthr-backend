import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IReEnumeration, NewReEnumeration } from '../re-enumeration.model';

export type PartialUpdateReEnumeration = Partial<IReEnumeration> & Pick<IReEnumeration, 'id'>;

type RestOf<T extends IReEnumeration | NewReEnumeration> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

export type RestReEnumeration = RestOf<IReEnumeration>;

export type NewRestReEnumeration = RestOf<NewReEnumeration>;

export type PartialUpdateRestReEnumeration = RestOf<PartialUpdateReEnumeration>;

export type EntityResponseType = HttpResponse<IReEnumeration>;
export type EntityArrayResponseType = HttpResponse<IReEnumeration[]>;

@Injectable({ providedIn: 'root' })
export class ReEnumerationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/re-enumerations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(reEnumeration: NewReEnumeration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reEnumeration);
    return this.http
      .post<RestReEnumeration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(reEnumeration: IReEnumeration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reEnumeration);
    return this.http
      .put<RestReEnumeration>(`${this.resourceUrl}/${this.getReEnumerationIdentifier(reEnumeration)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(reEnumeration: PartialUpdateReEnumeration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(reEnumeration);
    return this.http
      .patch<RestReEnumeration>(`${this.resourceUrl}/${this.getReEnumerationIdentifier(reEnumeration)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestReEnumeration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestReEnumeration[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getReEnumerationIdentifier(reEnumeration: Pick<IReEnumeration, 'id'>): number {
    return reEnumeration.id;
  }

  compareReEnumeration(o1: Pick<IReEnumeration, 'id'> | null, o2: Pick<IReEnumeration, 'id'> | null): boolean {
    return o1 && o2 ? this.getReEnumerationIdentifier(o1) === this.getReEnumerationIdentifier(o2) : o1 === o2;
  }

  addReEnumerationToCollectionIfMissing<Type extends Pick<IReEnumeration, 'id'>>(
    reEnumerationCollection: Type[],
    ...reEnumerationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const reEnumerations: Type[] = reEnumerationsToCheck.filter(isPresent);
    if (reEnumerations.length > 0) {
      const reEnumerationCollectionIdentifiers = reEnumerationCollection.map(
        reEnumerationItem => this.getReEnumerationIdentifier(reEnumerationItem)!
      );
      const reEnumerationsToAdd = reEnumerations.filter(reEnumerationItem => {
        const reEnumerationIdentifier = this.getReEnumerationIdentifier(reEnumerationItem);
        if (reEnumerationCollectionIdentifiers.includes(reEnumerationIdentifier)) {
          return false;
        }
        reEnumerationCollectionIdentifiers.push(reEnumerationIdentifier);
        return true;
      });
      return [...reEnumerationsToAdd, ...reEnumerationCollection];
    }
    return reEnumerationCollection;
  }

  protected convertDateFromClient<T extends IReEnumeration | NewReEnumeration | PartialUpdateReEnumeration>(reEnumeration: T): RestOf<T> {
    return {
      ...reEnumeration,
      lastModified: reEnumeration.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restReEnumeration: RestReEnumeration): IReEnumeration {
    return {
      ...restReEnumeration,
      lastModified: restReEnumeration.lastModified ? dayjs(restReEnumeration.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestReEnumeration>): HttpResponse<IReEnumeration> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestReEnumeration[]>): HttpResponse<IReEnumeration[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
