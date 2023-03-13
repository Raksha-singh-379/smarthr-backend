import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocument, NewDocument } from '../document.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocument for edit and NewDocumentFormGroupInput for create.
 */
type DocumentFormGroupInput = IDocument | PartialWithRequiredKeyOf<NewDocument>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocument | NewDocument> = Omit<T, 'lastModified'> & {
  lastModified?: string | null;
};

type DocumentFormRawValue = FormValueOf<IDocument>;

type NewDocumentFormRawValue = FormValueOf<NewDocument>;

type DocumentFormDefaults = Pick<NewDocument, 'id' | 'lastModified'>;

type DocumentFormGroupContent = {
  id: FormControl<DocumentFormRawValue['id'] | NewDocument['id']>;
  docType: FormControl<DocumentFormRawValue['docType']>;
  docCategory: FormControl<DocumentFormRawValue['docCategory']>;
  fileName: FormControl<DocumentFormRawValue['fileName']>;
  contentType: FormControl<DocumentFormRawValue['contentType']>;
  fileUuid: FormControl<DocumentFormRawValue['fileUuid']>;
  folderUuid: FormControl<DocumentFormRawValue['folderUuid']>;
  refTable: FormControl<DocumentFormRawValue['refTable']>;
  refTableId: FormControl<DocumentFormRawValue['refTableId']>;
  companyId: FormControl<DocumentFormRawValue['companyId']>;
  status: FormControl<DocumentFormRawValue['status']>;
  lastModified: FormControl<DocumentFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<DocumentFormRawValue['lastModifiedBy']>;
};

export type DocumentFormGroup = FormGroup<DocumentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentFormService {
  createDocumentFormGroup(document: DocumentFormGroupInput = { id: null }): DocumentFormGroup {
    const documentRawValue = this.convertDocumentToDocumentRawValue({
      ...this.getFormDefaults(),
      ...document,
    });
    return new FormGroup<DocumentFormGroupContent>({
      id: new FormControl(
        { value: documentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      docType: new FormControl(documentRawValue.docType),
      docCategory: new FormControl(documentRawValue.docCategory),
      fileName: new FormControl(documentRawValue.fileName),
      contentType: new FormControl(documentRawValue.contentType),
      fileUuid: new FormControl(documentRawValue.fileUuid),
      folderUuid: new FormControl(documentRawValue.folderUuid),
      refTable: new FormControl(documentRawValue.refTable),
      refTableId: new FormControl(documentRawValue.refTableId),
      companyId: new FormControl(documentRawValue.companyId),
      status: new FormControl(documentRawValue.status),
      lastModified: new FormControl(documentRawValue.lastModified),
      lastModifiedBy: new FormControl(documentRawValue.lastModifiedBy),
    });
  }

  getDocument(form: DocumentFormGroup): IDocument | NewDocument {
    return this.convertDocumentRawValueToDocument(form.getRawValue() as DocumentFormRawValue | NewDocumentFormRawValue);
  }

  resetForm(form: DocumentFormGroup, document: DocumentFormGroupInput): void {
    const documentRawValue = this.convertDocumentToDocumentRawValue({ ...this.getFormDefaults(), ...document });
    form.reset(
      {
        ...documentRawValue,
        id: { value: documentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocumentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
    };
  }

  private convertDocumentRawValueToDocument(rawDocument: DocumentFormRawValue | NewDocumentFormRawValue): IDocument | NewDocument {
    return {
      ...rawDocument,
      lastModified: dayjs(rawDocument.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertDocumentToDocumentRawValue(
    document: IDocument | (Partial<NewDocument> & DocumentFormDefaults)
  ): DocumentFormRawValue | PartialWithRequiredKeyOf<NewDocumentFormRawValue> {
    return {
      ...document,
      lastModified: document.lastModified ? document.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
